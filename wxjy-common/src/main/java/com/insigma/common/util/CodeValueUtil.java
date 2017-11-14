package com.insigma.common.util;

import com.insigma.mvc.model.CodeValue;
import net.sf.ehcache.Element;

import java.util.List;

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
        Element element = EhCacheUtil.getManager().getCache("webcache").get(code_type);
        if (element != null) {
            List<CodeValue> list = (List<CodeValue>) element.getValue();
            for (CodeValue codevalue : list) {
                if (codevalue.getCode_name().equals(code_name)) {
                    return codevalue.getCode_value();
                }
            }
        }
        return null;
    }

    public static List<CodeValue> getCodeListByCodeType(String code_type) {
        Element element = EhCacheUtil.getManager().getCache("webcache").get(code_type);
        if (element != null) {
            List<CodeValue> list = (List<CodeValue>) element.getValue();
            return list;
        }
        return null;
    }
}
