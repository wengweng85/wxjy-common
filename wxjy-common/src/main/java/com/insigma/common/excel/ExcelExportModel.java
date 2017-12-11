package com.insigma.common.excel;


public class ExcelExportModel implements java.io.Serializable {

	private String excel_info; // varchar2(36) 导入临时表之临时表主键

	public String getExcel_info() {
		return excel_info;
	}

	public void setExcel_info(String excel_info) {
		this.excel_info = excel_info;
	}
	
	
	

}