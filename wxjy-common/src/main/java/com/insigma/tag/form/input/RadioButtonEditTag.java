package com.insigma.tag.form.input;

import com.insigma.common.util.CodeValueUtil;
import com.insigma.common.util.StringUtil;
import com.insigma.mvc.model.CodeValue;
import com.insigma.tag.form.constraint.TagConstraint;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.io.IOException;
import java.util.List;

/**
 * 自定义标签之按钮式radio
 *
 * @author liuds
 *
 */
public class RadioButtonEditTag implements Tag {

    private PageContext pageContext;

    // property
    private String property;

    private String codetype;

    //label
    private String label;

    //占位列数,包括label的一列
    private String cols;

    //是否必输
    private String required;

    // 值
    private String value;



    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }


    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PageContext getPageContext() {
        return pageContext;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getValue() {
        return value;
    }



    @Override
    public int doEndTag() throws JspException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int doStartTag() throws JspException {
        value=(value==null)?"":value;
        required=(required==null)?"":required;
        cols=(cols==null)? TagConstraint.COLS:cols;

        String [] col=cols.split(",");
        int labelcol=Integer.parseInt(col[0]);
        int inputcol=Integer.parseInt(col[1]);
        //是否必输
        boolean isrequired=Boolean.parseBoolean(required);
        JspWriter out = pageContext.getOut();
        StringBuffer sb=new StringBuffer();
        sb.append("<label class=\" col-xs-"+labelcol+"  col-sm-"+labelcol+"   control-label\">"+label);
        if(isrequired){
            sb.append("<span class=\"require\">*<span>");
        }
        sb.append("</label>");
        sb.append("<div class=\" col-xs-"+inputcol+"  col-sm-"+inputcol+"  \">");
        sb.append("<input type=\"hidden\" id=\""+property+"\" name=\""+property+"\"  value=\""+value+"\" class=\"radioButton\">");


        // 从EhCache获取下载
        List<CodeValue> list = CodeValueUtil.getCodeListByCodeType(codetype.toUpperCase());
        if (list != null) {
            for (CodeValue codevalue : list) {
                sb.append("<button type=\"button\" value=\"" + codevalue.getCode_value() + "\" ");
                if (StringUtil.isNotEmpty(value) && value.equals(codevalue.getCode_value())) {
                    sb.append(" class=\"btn btn-success mr-10 "+property+"\" ");
                }else{
                    sb.append(" class=\"btn btn-default mr-10 "+property+"\" ");
                }
                sb.append(" >"+codevalue.getCode_name()+"</button>");
            }
        }

        sb.append("</div>");

        sb.append("<script type=\"text/javascript\">");
        sb.append("$('."+property+"').click(function () {");
        sb.append("  $('."+property+"').addClass('btn-default');");
        sb.append("  $('."+property+"').removeClass('btn-success');");
        sb.append("  $(this).addClass('btn-success');");
        sb.append("  $(this).removeClass('btn-default');");
        sb.append("  $('#"+property+"').val($(this).val());");
        sb.append("});");
        sb.append("</script>");

        try {
            out.write(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public Tag getParent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void release() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPageContext(PageContext arg0) {
        // TODO Auto-generated method stub
        this.pageContext = arg0;
    }

    @Override
    public void setParent(Tag arg0) {
        // TODO Auto-generated method stub
    }

}
