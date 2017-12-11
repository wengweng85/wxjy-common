package com.insigma.common.util;

import java.util.List;

import com.insigma.mvc.model.CodeValue;

/**
 * 参数工具类
 */
public class CodeValueUtil {

    /**
     * 根据参数类型和参数名称获取参数编码
     *
     * @param code_name
     * @param code_type
     * @return
     */
    public static String getCodeValueByName(String code_name, String code_type) {
        Object object=EhCacheUtil.getParamFromCache(code_type);
        if (object != null) {
            List<CodeValue> list = (List<CodeValue>) object;
            for (CodeValue codevalue : list) {
                if (codevalue.getCode_name().equals(code_name)) {
                    return codevalue.getCode_value();
                }
            }
        }
        return null;
    }

    public static List<CodeValue> getCodeListByCodeType(String code_type) {
        Object object=EhCacheUtil.getParamFromCache(code_type);
        if (object != null) {
            List<CodeValue> list = (List<CodeValue>) object;
            return list;
        }
        return null;
    }
}
