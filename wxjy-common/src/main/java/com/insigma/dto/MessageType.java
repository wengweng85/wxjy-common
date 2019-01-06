package com.insigma.dto;

/**
 *  系统message类型
 */
public enum MessageType  {
	
	MESSAGE_TYPE_LOG("log","接口日志"),
	MESSAGE_TYPE_USERLOG("userlog","用户日志"),
	MESSAGE_TYPE_INFO("info","系统消息站内信"),
	MESSAGE_TYPE_EMAIL("email","邮件"),
	MESSAGE_TYPE_EMAIL_RESUME("emailResume","带简历的邮件"),
	MESSAGE_TYPE_SMS("sms","注册验证码"),
	MESSAGE_TYPE_SMS_VERCODE("smsCode","找回密码验证码"),
	MESSAGE_TYPE_SMS_LOGIN("smsCodeLogin","短信登录验证码");


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
