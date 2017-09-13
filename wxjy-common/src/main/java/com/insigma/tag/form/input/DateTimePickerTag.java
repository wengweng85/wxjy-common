package com.insigma.tag.form.input;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * �Զ����ǩ֮����ʱ��ѡ���
 * 
 * @author wengsh
 *
 */
public class DateTimePickerTag implements Tag {

	private PageContext pageContext;

	private String property;
	// ֵ
	private String value;

	// У�����
	private String validate;
	
	//label
    private String label;
					
	//ռλ����,����label��һ��
    private String cols;

	//�Ƿ����
	private String required;
	
	//�Ƿ�ֻ��
	private String readonly;


	public void setValue(String value) {
		this.value = value;
	}

	public PageContext getPageContext() {
		return pageContext;
	}

	

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
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

	
	
	public String getCols() {
		return cols;
	}

	public void setCols(String cols) {
		this.cols = cols;
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

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
	     //��ֵ���
		 validate=(validate==null)?"":validate;
	     value=(value==null)?"":value;
	     cols=(cols==null)?"1,2":cols;
	     required=(required==null)?"":required;
	     readonly=(readonly==null)?"false":readonly;
	     
	     String [] col=cols.split(",");
	     int labelcol=Integer.parseInt(col[0]);
	     int inputcol=Integer.parseInt(col[1]);
	     //�Ƿ����
	     boolean isrequired=Boolean.parseBoolean(required);
	     
	     //�Ƿ�ֻ��
	     boolean isreadonly=Boolean.parseBoolean(readonly);
	     
	     JspWriter out = pageContext.getOut();
	     StringBuffer sb=new StringBuffer();
	     sb.append("<label class=\"col-sm-"+labelcol+"  col-xs-"+labelcol+" control-label\">"+label);
	     if(isrequired){
	    	 sb.append("<span class=\"require\">*<span>");
	     }
	     sb.append("</label>");
	     sb.append("<div class=\"col-sm-"+inputcol+" col-xs-"+inputcol+" \">");
	     sb.append("<div class=\"input-group form_datetime date\">");
		 sb.append("<input type=\"text\" id=\""+property+"\" name=\""+property+"\" value=\""+value+"\"  placeholder=\"��ѡ��"+label+"\"    validate=\""+validate+"\" class=\"form-control\" ");
		 if(isreadonly){
			 sb.append(" readonly=\"readonly\" ");
		 }
		 sb.append(" > ");
		 
		 sb.append("<span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-remove\" ></span></span>");
		 sb.append("<span class=\"input-group-addon\" draggable=\"false\"><i class=\"fa fa-calendar\"></i></span>");
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