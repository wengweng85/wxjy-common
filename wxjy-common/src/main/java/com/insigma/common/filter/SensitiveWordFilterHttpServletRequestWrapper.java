package com.insigma.common.filter;

import com.insigma.common.util.SensitiveWordUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/**
 *
 * @author admin
 *
 */
public class SensitiveWordFilterHttpServletRequestWrapper extends HttpServletRequestWrapper {

    Log log= LogFactory.getLog(SensitiveWordFilterHttpServletRequestWrapper.class);

    private HttpServletRequest orgRequest;




    public SensitiveWordFilterHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values==null)  {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            if(SensitiveWordUtil.sensitiveWordMap.size()>0){
                encodedValues[i] = SensitiveWordUtil.replaceSensitiveWord(values[i], "");
            }
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value != null) {
            if(SensitiveWordUtil.sensitiveWordMap.size()>0){
                value= SensitiveWordUtil.replaceSensitiveWord(value, "");
            }
        }
        return value;
    }
    /**
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof SensitiveWordFilterHttpServletRequestWrapper) {
            return ((SensitiveWordFilterHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value != null&&!value.equals("")) {
            if(SensitiveWordUtil.sensitiveWordMap.size()>0){
                value= SensitiveWordUtil.replaceSensitiveWord(value, "");
            }
        }
        return value;
    }

}
