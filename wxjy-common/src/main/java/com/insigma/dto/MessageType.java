package com.insigma.dto;

/**
 *  系统message类型
 */
public enum MessageType  {
	
	MESSAGE_TYPE_LOG("log","接口日志"),
	MESSAGE_TYPE_USERLOG("userlog","用户日志"),
	MESSAGE_TYPE_INFO("info","系统消息站内信"),
	MESSAGE_TYPE_EMAIL("email","邮件"),
	MESSAGE_TYPE_SMS("sms","短信"),
	MESSAGE_TYPE_SMS_VERCODE("smsCode","短信验证码");
	
	private String code;
	private String name;
	
	MessageType(String code,String name){
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
