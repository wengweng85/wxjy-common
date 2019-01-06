package com.insigma.tag.form.footer;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.insigma.common.listener.AppConfig;


/**
 * 自定义标签之表单选择框标签
 * @author admin
 *
 */
public class CommonJsFooterTag implements Tag  {
	
	private PageContext pageContext;  

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
	  JspWriter out = pageContext.getOut();
	  // TODO Auto-generated method stub
	  HttpServletRequest request=((HttpServletRequest) this.pageContext.getRequest());
      String contextpath = request.getContextPath();
      String staticPath = AppConfig.getProperties("website_static_resource_url");
      if(null==staticPath||staticPath.equals("")){
		  staticPath=contextpath;
	  }
	  StringBuffer sb=new StringBuffer();
	  
	  String csrf=(String)request.getAttribute("csrf" );
	  
	//<!-- 用于记录当前项目根目录供js调用 -->
	  sb.append("<input type='hidden' id='contextPath' name='contextPath' value='"+contextpath+"'>");
	  //<!-- 隐藏域用于重复校验验证 -->
	  sb.append("<input type='hidden' id='CSRFToken' name='CSRFToken' value='"+csrf+"'>");
	  
	  
	  sb.append("<script type=\"text/javascript\" src=\"http://api.map.baidu.com/api?v=2.0&ak=HZCXQq4lNQ0jOOnCBbpaNmuMgWkLN2TW\"></script>");
	  sb.append("<script type=\"text/javascript\" src=\"http://api.map.baidu.com/library/AreaRestriction/1.2/src/AreaRestriction_min.js\"></script>");
	  //<!--css及javascript引入开始-->
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/bootstrap.min.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/layer/layer.min.js'></script>");
	  //<!--bootstrap-table  -->
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/bootstrap-table/bootstrap-table.min.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/bootstrap-table/extensions/export/bootstrap-table-export.min.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/tableExport/tableExport.min.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/bootstrap-table/extensions/filter-control/bootstrap-table-filter-control.min.js'></script>");

	  //<!-- ztree  -->
	  sb.append("<script  src='"+staticPath+"/resource/hplus/js/jQuery/ztree/jquery.ztree.all.min.js'></script>");
	
	  //<!-- select -->
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/bootstrap-select/js/bootstrap-select.min.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/bootstrap-select/js/i18n/defaults-zh_CN.min.js'></script>");
	  
	  //<!--select2-->
	  //sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/select2/js/select2.full.min.js'></script>");
	  //sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/select2/js/i18n/zh-CN.js'></script>");
	  
	  // <!-- datetimepicker -->
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js'></script>");
	  
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/json2.js'></script>");
	  
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/contabs.js'></script>");
	  
	  // <!-- bootstrap-editable -->
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/bootstrap3-editable/js/bootstrap-editable.min.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/plugins/bootstrap-table/extensions/editable/bootstrap-table-editable.min.js'></script>");
	  
	  //<!--bootstrap-switch-->
	  sb.append("<script src='"+staticPath+"/resource/hplus/css/plugins/bootstrap-switch-master/dist/js/bootstrap-switch.min.js'></script>");
	  
	 // sb.append("<script src='"+staticPath+"/resource/hplus/js/jquery.inputmask.js'></script>");
	 // sb.append("<script src='"+staticPath+"/resource/hplus/js/jquery.inputmask.date.extensions.js'></script>");
	 // sb.append("<script src='"+staticPath+"/resource/hplus/js/jquery.inputmask.extensions.js'></script>");
	
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/bootstrap-paginator.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/bootstrap-closable-tab.js'></script>");
	  
	  //<!--rc about js--> 
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/rc.all-2.0.js'></script>");
	  sb.append("<script src='"+staticPath+"/resource/hplus/js/rc.tag-1.0.js'></script>");
	  
	  
	  //sb.append("<script src='"+staticPath+"/sys/codetype/getAreaData'></script>");
	  //sb.append("<script src='"+staticPath+"/resource/hplus/js/selector/area_select.js'></script>");
	  
	  //<!-- 模型 -->
	  sb.append("<script id=\"tpl_option\" type=\"text/x-handlebars-template\" >");
	  sb.append("  {{#each this}}");
	  sb.append("  <option value='{{code_value}}'   {{#equals code_value ../value }} selected {{/equals}}   >{{code_name}}</option>");
	  sb.append("  {{/each}}");
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
