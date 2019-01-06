package com.insigma.common.util;

import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



/**
 * 利用HttpClient进行post请求的工具类
 * @author xxx
 *
 */
public class HttpClientUtil {
	private static String  CHARTSET="UTF-8";
	private static Log log= LogFactory.getLog(HttpClientUtil.class);
	/**
	 * doPost
	 * @param url
	 * @param map
	 * @return
	 */
	public static JSONObject doPost(String url,Map<String,String> map){
		return doPost(url,map,CHARTSET);
	}

	/**
	 * doPost
	 * @param url
	 * @return
	 */
	public static JSONObject doPost(String url){
		return doPost(url,null,CHARTSET);
	}

	/**
	 * doPost
	 * @param url
	 * @param map
	 * @param charset
	 * @return
	 */
	public static JSONObject doPost(String url,Map<String,String> map,String charset){
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		HttpResponse response = null;
		try{
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			//设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			if(map!=null){
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String,String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
				}
				if(list.size() > 0){
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
					httpPost.setEntity(entity);
				}
			}
			response = httpClient.execute(httpPost);
			if(response != null){
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						result = EntityUtils.toString(resEntity,charset);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		log.info(result);
		JSONObject jsonobject = JSONObject.fromObject(result);
		return jsonobject;
	}

	/**
	 * doGet
	 * @param url
	 * @param charset
	 * @return
	 */
	public static JSONObject doGet(String url,String charset){
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String result = null;
		try{
			httpClient = new SSLClient();
			httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if(response != null){
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity resEntity = response.getEntity();
					if(resEntity != null){
						result = EntityUtils.toString(resEntity,charset);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		log.info(result);
		JSONObject jsonobject = JSONObject.fromObject(result);
		return jsonobject;
	}

	/**
	 * doPostJson
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPostJson(String url, String json) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String result = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(response.getEntity(), "utf-8");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}


	/**
	 * doGet
	 * @param url
	 * @return
	 */
	public static JSONObject doGet(String url){
		return doGet(url,CHARTSET);
	}

	public static void main(String [] a){
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		httpClientUtil.doGet("http://www.epsoft.com.cn/", "utf-8");
	}
}

