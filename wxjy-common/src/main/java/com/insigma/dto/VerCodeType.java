package com.insigma.dto;

/**
 * 验证码类型
 */
public enum VerCodeType {
    VerCode_TYPE_SMS_MOBILEREG("SMS_MOBILEREG","手机号注册验证码"),
    VerCode_TYPE_SMS_PWDBACK("SMS_PWDBACK","手机密码找回验证码");

    private String code;
    private String name;

    VerCodeType(String code,String name){
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
