package com.insigma.mvc;

import java.util.HashMap;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;


/**
 * mvc帮助类，主要用于包装controller以及serviceimp层返回的数据
 * 将其包装成类返回成json格式
 *
 * @author admin
 */
public class MvcHelper<T> {

    /**
     * 校验并返回对象
     *
     * @param result
     * @return
     */
    public AjaxReturnMsg<String> validate(BindingResult result) {
        FieldError fielderror = result.getFieldErrors().get(result.getErrorCount() - 1);
        AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
        dto.setSuccess(false);
        dto.setMessage(fielderror.getDefaultMessage());
        dto.setObj(fielderror.getField());
        return dto;
    }

    /**
     * 成功返回
     *
     * @param message
     * @return
     */
    public AjaxReturnMsg<String> success(String message) {
        AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
        dto.setSuccess(true);
        dto.setMessage(message);
        return dto;
    }

    /**
     * 成功返回
     *
     * @param o
     * @return
     */
    public AjaxReturnMsg<T> success(String message, T o) {
        AjaxReturnMsg<T> dto = new AjaxReturnMsg<T>();
        dto.setSuccess(true);
        dto.setMessage(message);
        dto.setObj(o);
        return dto;
    }

    @SuppressWarnings("rawtypes")
    public AjaxReturnMsg success_obj(String message, Object o) {
        AjaxReturnMsg dto = new AjaxReturnMsg();
        dto.setSuccess(true);
        dto.setMessage(message);
        dto.setObj(o);
        return dto;
    }

    /**
     * 成功返回
     *
     * @param o
     * @return
     */
    public AjaxReturnMsg<T> success(T o) {
        AjaxReturnMsg<T> dto = new AjaxReturnMsg<T>();
        dto.setSuccess(true);
        dto.setObj(o);
        return dto;
    }

    /**
     * 成功返回 返回分页面信息 用于接口返回
     *
     * @param pageinfo
     * @return
     */
    public AjaxReturnMsg success(PageInfo<T> pageinfo) {
        AjaxReturnMsg dto = new AjaxReturnMsg();
        dto.setSuccess(true);
        dto.setObj(pageinfo);
        dto.setTotal(pageinfo.getTotal());
        return dto;
    }

    /**
     * 成功返回分页用于manager管理平台
     *
     * @param pageinfo
     * @return
     */
    public HashMap<String, Object> success_hashmap_response(PageInfo<T> pageinfo) {
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("total", pageinfo.getTotal());
        hashmap.put("rows", pageinfo.getList());
        return hashmap;
    }


    /**
     * 成功返回
     *
     * @param pageinfo
     * @return
     */
    public HashMap<String, Object> success_hashmap_response(PageInfo<T> pageinfo, Object o) {
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("pageinfo", pageinfo);
        hashmap.put("obj", o);
        return hashmap;
    }

    /**
     * 错误返回
     *
     * @param message
     * @return
     */
    public AjaxReturnMsg<String> error(String message) {
        AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
        dto.setSuccess(false);
        dto.setMessage(message);
        return dto;
    }

    /**
     * 错误返回
     *
     * @param message
     * @param obj
     * @return
     */
    public AjaxReturnMsg<T> error(String message, T obj) {
        AjaxReturnMsg<T> dto = new AjaxReturnMsg<T>();
        dto.setSuccess(false);
        dto.setMessage(message);
        dto.setObj(obj);
        return dto;
    }

    /**
     * 错误返回
     *
     * @param o
     * @return
     */
    public AjaxReturnMsg<T> error(T o) {
        AjaxReturnMsg<T> dto = new AjaxReturnMsg<T>();
        dto.setSuccess(false);
        dto.setObj(o);
        return dto;
    }

    /**
     * 错误返回
     *
     * @param e
     * @return
     */
    public AjaxReturnMsg<String> error(Exception e) {
        AjaxReturnMsg<String> dto = new AjaxReturnMsg<String>();
        dto.setSuccess(false);
        dto.setMessage(e.getLocalizedMessage());
        return dto;
    }

}
