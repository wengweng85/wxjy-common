package com.insigma.dto;

/**
 * ajax信息返回
 * @author wengsh
 * @date 2012-9-27
 *
 */
public class AjaxReturnMsg<T> implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String syscode; //系统返回状态码,默认200
	private String success; //业务状态码，默认为true
	private String message; //业务状态中文说
	
	private T obj;//对象
	private Long total;//分页面对象之总数
	
	public AjaxReturnMsg(){
		this.syscode=SysCode.SYS_CODE_200.getCode();//系统返回状态码,默认200
		this.success="true";//业务状态码，默认为true
	}
	
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSyscode() {
		return syscode;
	}
	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	
	

}
