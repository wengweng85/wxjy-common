package com.insigma.common.excel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * excel文件导出相关工具类之数据格式校验器
 * @author admin
 * 
 *
 */
public class ExcelDataFormatValidUtil {
	
	/**
	 * 是否是空值
	 * @param data
	 * @return true 校验失败 false 校验成功
	 */
	public boolean isNull(String data){
		if(null==data||data.equals("")){
			 return true;
		}else{
			return false;
		}
	}
	/**
	 *  是否是日期格式
	 * @param data
	 * @param format 要校验的日期格式
	 * @return
	 */
	public boolean isDate(String data,String format){
		if(null==data||data.equals("")){
			 return false;
		}else{
			data=data.replace(".", "/").replace("-", "/");
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				 sdf.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
				return true;
			}
			return false;
		}
	}
	//长度校验
	//数字格式校验
	
}
