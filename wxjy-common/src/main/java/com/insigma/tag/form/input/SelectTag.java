package com.insigma.tag.form.input;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.insigma.common.util.EhCacheUtil;
import com.insigma.mvc.model.CodeValue;
import com.insigma.tag.form.constraint.TagConstraint;

/**
 * 自定义标签之表单选择框标签
 *
 * @author admin
 */
public class SelectTag implements Tag {

    private PageContext pageContext;

    // 下拉代码类型
    private String codetype;

    // property
    private String property;


    //label
    private String label;

    //占位列数,包括label的一列
    private String cols;

    //显示列数
    private String size;

    //是否必输
    private String required;

    private String readonly;

    private String filter;

    //是否动态获取
    private String dynamic;

    // 值
    private String value;

    // 校验规则
    private String validate;

    //是否多选
    private String multiple;

    //onclick事件
    private String onclick;

    //onchange事件
    private String onchange;

    //onblur事件
    private String onblur;

    //onkeydown事件
    private String onkeydown;

    //onkeypress事件
    private String onkeypress;

    //onkeyup事件
    private String onkeyup;
    
    //placeholder
  	private String placeholder;
  	
  	//是否隐藏空选项
  	private String hiddennull;


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
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

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnchange() {
        return onchange;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }

    public String getOnblur() {
        return onblur;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getOnkeydown() {
        return onkeydown;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeypress() {
        return onkeypress;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnkeyup() {
        return onkeyup;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PageContext getPageContext() {
        return pageContext;
    }


    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public String getCodetype() {
        return codetype;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }


    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }
    
	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getHiddennull() {
		return hiddennull;
	}

	public void setHiddennull(String hiddennull) {
		this.hiddennull = hiddennull;
	}

	@Override
    public int doEndTag() throws JspException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int doStartTag() throws JspException {
        // 空值检查
        validate = (validate == null) ? "" : validate;
        value = (value == null) ? "" : value;
        multiple = (multiple == null) ? "false" : multiple;
        dynamic = (dynamic == null) ? "false" : dynamic;
        required = (required == null) ? "" : required;
        readonly = (readonly == null) ? "" : readonly;
        codetype = (codetype == null) ? "" : codetype;
        filter = (filter == null) ? "" : filter;
        cols = (cols == null) ? TagConstraint.COLS : cols;
        size = (size == null) ? "8" : size;
        hiddennull = (hiddennull == null) ? "false" : hiddennull;

        String[] col = cols.split(",");
        int labelcol = Integer.parseInt(col[0]);
        int inputcol = Integer.parseInt(col[1]);
        //是否只读
        boolean isreadonly = Boolean.parseBoolean(readonly);
        //是否必输
        boolean isrequired = Boolean.parseBoolean(required);
        boolean isdynamic = Boolean.parseBoolean(dynamic);
        String placeholders = placeholder;
		if(placeholder == null){
			placeholders = "" + label;
		}
        JspWriter out = pageContext.getOut();
        StringBuffer sb = new StringBuffer();
        sb.append("<label class=\" col-xs-" + labelcol + "  col-sm-" + labelcol + "   control-label\">" + label);
        if (isrequired) {
            sb.append("<span class=\"require\">*</span>");
        }
        sb.append("</label>");
        sb.append("<div class=\" col-xs-" + inputcol + "  col-sm-" + inputcol + "  \">");
        sb.append("<select class=\"form-control selectpicker \" id=\"" + property + "\" name=\"" + property + "\"  title=\"" + placeholders + "\" value=\"" + value + "\"  selectOnTab=\"true\" data-actions-box=\"true\" data-size=\"" + size + "\"  data-live-search=\"true\" validate=\"" + validate + "\"   data-selected-text-format=\"count > 2\"");

        if (isreadonly) {
            sb.append(" disabled ");
        }

        //onclick事件
        if (onclick != null) {
            sb.append(" onclick=\"" + onclick + "\" ");
        }
        //onblur
        if (onblur != null) {
            sb.append(" onblur=\"" + onblur + "\" ");
        }
        //onkeypress
        if (onkeypress != null) {
            sb.append(" onkeypress=\"" + onkeypress + "\" ");
        }
        //onkeydown
        if (onkeydown != null) {
            sb.append(" onkeydown=\"" + onkeydown + "\" ");
        }
        //onkeyup
        if (onkeyup != null) {
            sb.append(" onkeyup=\"" + onkeyup + "\" ");
        }
        //onchange
        if (onchange != null) {
            sb.append(" onchange=\"" + onchange + "\" ");
        }
        if (Boolean.parseBoolean(multiple)) {
            sb.append(" multiple ");
        }
        sb.append(">");

        //如果codetype不为空且过滤条件为空
        if (!"".equals(codetype) && "".equals(filter)) {
            // 从EhCache获取下载
            Object object = EhCacheUtil.getParamFromCache(codetype.toUpperCase());
            if (object != null) {
                List<CodeValue> list = (List<CodeValue>) object;
                if (!Boolean.parseBoolean(multiple) && !Boolean.parseBoolean(hiddennull)) {
                    sb.append("<option value=\"\">" + placeholders + "</option> ");
                }
                for (CodeValue codevalue : list) {
                    sb.append("<option ");
                    if (value != null && !"".equals(value)) {
                        if (value.equals(codevalue.getCode_value())) {
                            sb.append(" selected ");
                        }
                    }
                    sb.append("  value=\"" + codevalue.getCode_value() + "\">" + codevalue.getCode_name() + "</option>");
                }
            }
        }
        sb.append("</select>");


        sb.append("</div>");
        sb.append("<script type=\"text/javascript\">");

        //如果过滤条件不为空、说明代码数据要通过动态过滤方式获取
        if (!"".equals(filter)) {
            sb.append(" $(function() { rc_tag.dynamic_get_codevalue_by_codetype_and_filter(\"#" + property + "\",\"" + codetype + "\",\"" + filter + "\",\"" + value + "\");})");
        } else {
            if (isdynamic) {
                sb.append(" $(function() { rc_tag.dynamic_get_codevalue_by_codetype(\"#" + property + "\",\"" + codetype + "\",\"" + value + "\");})");
            }
        }
        sb.append("</script>");

        // 从redis获取下载
        /*
         * try{ RedisManager redismanager=
         * MyApplicationContextUtil.getContext().getBean(RedisManager.class);
         * List<CodeValue> list=(List<CodeValue>)redismanager.get(codetype);
         * if(list!=null){ for(CodeValue codevalue:list){ sb.append("<option ");
         * if(value!=null&&!value.equals("")){
         * if(value.equals(codevalue.getCode_value())){ sb.append(" selected ");
         * } } sb.append("  value=\""+codevalue.getCode_value()+"\">"+codevalue.
         * getCode_name()+"</option>"); } } sb.append("</select>");
         * }catch(Exception e){ e.printStackTrace(); }
         */
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
