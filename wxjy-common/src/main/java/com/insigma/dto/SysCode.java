package com.insigma.dto;

/**
 *  系统请求状态码
 */
public enum SysCode  {
	
	SYS_CODE_200("200","成功"),
	SYS_APPKEY_EMPTY("40001","appkey为空"),
	SYS_APPKEY_ERROR("40002","appkey不正确"),
	SYS_API_EXCEPTION("50001","api发生异常");
	
	private String code;
	private String name;
	
	private SysCode(String code,String name){
		this.code=code;
		this.name=name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
