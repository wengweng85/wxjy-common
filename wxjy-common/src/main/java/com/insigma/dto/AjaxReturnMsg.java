package com.insigma.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ajax信息返回
 *
 * @author wengsh
 * @date 2012-9-27
 */

@ApiModel(value="接口通用返回类形态为json")
public class AjaxReturnMsg<T> implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "系统返回状态码",value = "\"200\",\"成功\" \"40001\",\"appkey为空\"\"40002\",\"appkey不正确\"\"40003\",\"token为空,请先登录\"\"40004\",\"token值不正确或已经过期,请重新登录\"\"40005\",\"登录信息与token信息不匹配,非法请求请确认\"\"40006\",\"没有访问此服务的权限或地址地址,请确认\"\"50001\",\"api发生异常\"")
    private String syscode; // 系统返回状态码,默认200
    @ApiModelProperty(name="业务状态码",value="true 成功 flase 失败")
    private boolean success; // 业务状态码，默认为true
    @ApiModelProperty(name="业务状态中文说明",value="业务状态中文说明")
    private String message = ""; // 业务状态中文说明
    @ApiModelProperty(name="业务状态中文状态码",value="业务状态中文状态码")
    private String code = "";
    @ApiModelProperty(name="业务返回对象集合",value="业务返回对象集合")
    private T obj; //对象
    @ApiModelProperty(name="分页对象之总数",value="分页对象之总数")
    private Long total; //分页面对象之总数

    public AjaxReturnMsg() {
        this.syscode = SysCode.SYS_CODE_200.getCode(); // 系统返回状态码,默认200
        this.success = true; // 业务状态码，默认为true
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

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
