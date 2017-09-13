package com.insigma.dto;

/**
 * ajax��Ϣ����
 * @author wengsh
 * @date 2012-9-27
 *
 */
public class AjaxReturnMsg<T> implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String syscode; //ϵͳ����״̬��,Ĭ��200
	private String success; //ҵ��״̬�룬Ĭ��Ϊtrue
	private String message; //ҵ��״̬����˵
	
	private T obj;//����
	private Long total;//��ҳ�����֮����
	
	public AjaxReturnMsg(){
		this.syscode=SysCode.SYS_CODE_200.getCode();//ϵͳ����״̬��,Ĭ��200
		this.success="true";//ҵ��״̬�룬Ĭ��Ϊtrue
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
