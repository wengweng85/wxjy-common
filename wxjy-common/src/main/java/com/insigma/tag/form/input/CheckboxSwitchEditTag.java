package com.insigma.tag.form.input;

import com.insigma.tag.form.constraint.TagConstraint;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * 自定义标签之checkbox switch选择
 * 主要用于是否选择框
 * 
 * @author admin
 *
 */
public class CheckboxSwitchEditTag implements Tag {

	private PageContext pageContext;

	// property
	private String property;

	//label
   private String label;
			
	//占位列数,包括label的一列
   private String cols;

	// 值
	private String value;

	

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
	     cols=(cols==null)? TagConstraint.COLS:cols;
	     String [] col=cols.split(",");
	     int labelcol=Integer.parseInt(col[0]);
	     int inputcol=Integer.parseInt(col[1]);
	     JspWriter out = pageContext.getOut();
	     StringBuffer sb=new StringBuffer();
	     sb.append("<label class=\" col-xs-"+labelcol+"  col-sm-"+labelcol+"   control-label\">"+label);
	     sb.append("</label>");
	     sb.append("<div class=\"  col-xs-"+inputcol+"  col-sm-"+inputcol+"  \">");
		 sb.append("<input name=\""+property+"\"  class=\"js-switch\"  type=\"checkbox\" id=\""+property+"\" value=\""+value+"\"" );
		 if (value.equals("1")) {
			sb.append(" checked=\"checked\" ");
		 }
		 sb.append(">");
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
