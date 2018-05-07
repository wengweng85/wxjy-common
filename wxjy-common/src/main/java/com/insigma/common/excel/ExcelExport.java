package com.insigma.common.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.csvreader.CsvWriter;
import com.insigma.common.util.RandomNumUtil;


/**
 * excel文件导出相关工具类
 * @author wengsh
 * 
 *
 */
public class ExcelExport<T> {
	
	private static Log log=LogFactory.getLog(ExcelExport.class);
	
	public String sqlFileTranserToCvs(Connection connection,String sql) throws SQLException,IOException{
		// 判断文件是否存在,存在则删除,然后创建新表格
		String prefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ RandomNumUtil.getRandomString(6);
		File tmp =File.createTempFile(prefix,"cvs");
		// 创建CSV写对象
		CsvWriter csvWriter = new CsvWriter(tmp.getPath(),',', Charset.forName("GBK"));

		// 数据查询开始
		PreparedStatement	preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		// 获取结果集表头
		ResultSetMetaData md = resultSet.getMetaData();
		int columnCount = md.getColumnCount();

		String[] columnNameList=new String[columnCount];
		List<String[]> list = new ArrayList<>();
		for (int i = 0; i < columnCount; i++) {
			columnNameList[i]=md.getColumnName(i);
		}
		list.add(columnNameList);

		csvWriter.writeRecord(columnNameList);

		// 数据记录数
		int i = 0;
		// 临时数据存储
		StringBuffer stringBuffer = new StringBuffer();

		while (resultSet.next()) {

		    // 记录号
		    i++;
		    // 依据列名获取各列值
		    for (int j = 1; j<=columnCount; j++){

		        String value = resultSet.getString(j);
		        //创建列
		        stringBuffer.append(value);
		        if (j != columnCount){
		            stringBuffer.append(',');
		        }
		    }

		    String buffer_string = stringBuffer.toString();
		    String[] content = buffer_string.split(",");
		    csvWriter.writeRecord(content);
		    stringBuffer.setLength(0);
		}

		// 文件输出
		csvWriter.flush();
		csvWriter.close();
		return tmp.getPath();
	}
	

	/**
	 * exportExcel
	 * @param headers
	 * @param dataset
	 * @param fileName
	 */
	public String  exportCvs(String[] headers,Collection<T> dataset, String fileName)  throws SQLException,IOException{  
		//查询excel导出数据开始
		log.info("生成csv格式文件开始"+new Date().toLocaleString());
		Date start=new Date();
		// 判断文件是否存在,存在则删除,然后创建新表格
		File cvsfilepath =File.createTempFile("excel_error_",".csv");
		// 创建CSV写对象
		CsvWriter csvWriter = new CsvWriter(cvsfilepath.getPath(),',', Charset.forName("GBK"));
		csvWriter.writeRecord(headers);
		 // 遍历集合数据，产生数据行  
        Iterator<T> it = dataset.iterator();  
        while (it.hasNext()) {  
        	ExcelExportModel t = (ExcelExportModel) it.next();  
        	csvWriter.writeRecord(t.getExcel_info().split(","));
        }
		csvWriter.close();  
		Date end=new Date();
		Long cost=end.getTime()-start.getTime();
		log.info("生成csv格式文件结束"+new Date().toLocaleString()+"花费="+cost);
		return cvsfilepath.getPath();
    }  
	
	/**
	 * exportExcel
	 * @param headers
	 * @param dataset
	 * @param fileName
	 */
	public String  exportExcel(String[] headers,Collection<T> dataset, String fileName) {  
        // 声明一个工作薄  
        XSSFWorkbook workbook = new XSSFWorkbook();  
        // 生成一个表格  
        XSSFSheet sheet = workbook.createSheet("Sheet1");  
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 20);  
        // 产生表格标题行  
        XSSFRow row = sheet.createRow(0);  
        for (short i = 0; i < headers.length; i++) {  
            XSSFCell cell = row.createCell(i);  
            XSSFRichTextString text = new XSSFRichTextString(headers[i].split(",")[0]);  
            cell.setCellValue(text);  
        }  
        try {  
            // 遍历集合数据，产生数据行  
            Iterator<T> it = dataset.iterator();  
            int index = 0;  
            while (it.hasNext()) {  
                index++;  
                row = sheet.createRow(index);  
                T t = (T) it.next();  
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值  
                Field[] fields = t.getClass().getDeclaredFields();  
                for (short i = 0; i < headers.length; i++) {  
                    XSSFCell cell = row.createCell(i);  
                    String fieldName = headers[i].split(",")[1];
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);  
                    Class tCls = t.getClass();  
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});  
                    Object value = getMethod.invoke(t, new Object[] {});  
                    // 判断值的类型后进行强制类型转换  
                    String textValue ="" ;  
                    // 其它数据类型都当作字符串简单处理  
                    if(value !=null  && value != ""){  
                        textValue = value.toString();  
                    }  
                    if (textValue !=null ) {  
                        XSSFRichTextString richString = new XSSFRichTextString(textValue);  
                        cell.setCellValue(richString);  
                    }  
                }  
            }  
            return getExportedFile(workbook);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return  null;
        }   
    }  
      
    /** 
     *  getExportedFile
     * 方法说明: 指定路径下生成EXCEL文件 
     * @return 
     */  
    public String getExportedFile(XSSFWorkbook workbook) throws Exception {  
    	FileOutputStream fos =null ;  
        try {  
            File file=File.createTempFile("excel_export_", ".xlsx");
            fos=new FileOutputStream(file);
            workbook.write(fos); 
            return file.getPath();
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;
        } finally {  
            if (fos !=null ) {  
                fos.close();  
            }  
        }  
    }  
}
