package com.insigma.json;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * json解析工具类
 * @author admin
 *
 * @param <T>
 */
public class JsonParseUtil<T> {
	
	
    public  static JsonConfig jsonConfig;
	
	public JsonParseUtil(){
		MyDateJsonBeanProcessor processor = new MyDateJsonBeanProcessor(); 
		jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor());
		jsonConfig.registerJsonBeanProcessor(java.sql.Date.class, processor);
		jsonConfig.registerJsonBeanProcessor(java.sql.Timestamp.class, processor);
	}
	
	public  JSONObject toJsonObject(T t){
		JSONObject jsonParam=JSONObject.fromObject(t,jsonConfig);
		return jsonParam;
	}
	
	public T toBean(JSONObject jsonobject,Class c){
		 return (T) JSONObject.toBean(jsonobject,c,jsonConfig); 
	}
	

}
