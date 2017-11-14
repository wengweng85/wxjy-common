package com.insigma.dto;

/**
 *  消息类型
 */
public enum InfoType  {
	
	I100000("I10000"," 获得个人基本信息"),
	I100001("I10001","保存个人基本信息");
	private String code;
	private String name;
	
	private InfoType(String code,String name){
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
