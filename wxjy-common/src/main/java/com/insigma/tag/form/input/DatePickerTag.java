package com.insigma.tag.form.input;

import com.insigma.common.util.StringUtil;
import com.insigma.tag.form.constraint.TagConstraint;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * 自定义标签之日期选择框
 * 
 * @author admin
 *
 */
public class DatePickerTag implements Tag {

	private PageContext pageContext;

	private String property;
	// 值
	private String value;
	// 校验规则
	private String validate;
	
	//label
    private String label;
					
	//占位列数,包括label的一列
    private String cols;

	//是否必输
	private String required;
	
	//是否只读
	private String readonly;

	//placeholder
	private String placeholder;
	//开始时间对应控件ID
	private String startDate;
	//结束时间对应控件ID
	private String endDate;


	public PageContext getPageContext() {
		return pageContext;
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

	public void setValue(String value) {
		this.value = value;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
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

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
	     //空值检查
		 validate=(validate==null)?"":validate;
	     value=(value==null)?"":value;
	     cols=(cols==null)? TagConstraint.COLS:cols;
	     
	     required=(required==null)?"":required;
	     readonly=(readonly==null)?"false":readonly;
	     
	     String [] col=cols.split(",");
	     int labelcol=Integer.parseInt(col[0]);
	     int inputcol=Integer.parseInt(col[1]);
	     //是否必输
	     boolean isrequired=Boolean.parseBoolean(required);
	     //是否只读
	     boolean isreadonly=Boolean.parseBoolean(readonly);
		String placeholders = placeholder;
		if(placeholder == null){
			placeholders = "" + label;
		}
	     
	     JspWriter out = pageContext.getOut();
	     StringBuffer sb=new StringBuffer();
	     sb.append("<label class=\" col-xs-"+labelcol+"  col-sm-"+labelcol+"   control-label\">"+label);
	     if(isrequired){
	    	 sb.append("<span class=\"require\">*<span>");
	     }
	     sb.append("</label>");
	     sb.append("<div class=\"   col-xs-"+inputcol+"  col-sm-"+inputcol+"  \">");
	     sb.append("<div class=\"input-group form_date date\" id=\""+property+"_date\" >");
		 sb.append("<input type=\"text\" id=\""+property+"\" name=\""+property+"\"  value=\""+value+"\"  placeholder=\""+placeholders+"\"   validate=\""+validate+"\" class=\"form-control\"  ");
		 
		 if(isreadonly){
			 sb.append(" readonly=\"readonly\" ");
		 }
		 if(StringUtil.isNotEmpty(startDate)){
			 sb.append(" startDate=\""+startDate+"\" ");
		 }
		if(StringUtil.isNotEmpty(endDate)){
			sb.append(" endDate=\""+endDate+"\" ");
		}
		 sb.append(" > ");
		 
		 sb.append("<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-remove\" ></span></span>");
		 sb.append("<span class=\"input-group-addon\" draggable=\"false\"><i class=\"glyphicon glyphicon-calendar\"></i></span>");
		 sb.append("</div>");
		 sb.append("</div>");
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
