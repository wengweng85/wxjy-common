package com.insigma.dto;

/**
 *  系统请求状态码
 */
public enum SysCode  {
	
	SYS_CODE_200("200","成功"),
	SYS_API_RATELIMIT("401","访问次数受限"),
	SYS_APPKEY_EMPTY("40001","appkey为空"),
	SYS_APPKEY_ERROR("40002","appkey不正确"),
	SYS_TOKEN_EMPTY("40003","token为空,请先登录"),
	SYS_TOKEN_ERROR("40004","token值不正确或已经过期,请重新登录"),
	SYS_USERID_ERROR("40005","登录信息与token信息不匹配,非法请求请确认"),
	SYS_SERVICEURL_ERROR("40006","没有访问此服务的权限或地址地址,请确认"),
	SYS_API_EXCEPTION("50001","api发生异常");

	
	private String code;
	private String name;
	
	SysCode(String code,String name){
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
