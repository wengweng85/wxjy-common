package com.insigma.common.rsa;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class SignUtils {
	
    
    private final static String KEY = "544BFCD7B77AA5B0CE22F86F7AB0E722";
    
    private final static String ALGORITHM = "SHA-512";
    
	/**
	 * 根据给定摘要算法名称创建一个消息摘要实例
	 */
	static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
	}


	/**
	 * 获取 SHA-512 消息摘要实例
	 * 
	 * @return SHA-512 消息摘要实例
	 * @throws RuntimeException
	 *             当 {@link NoSuchAlgorithmException} 发生时
	 */
	public static MessageDigest getSha512Digest() {
		return getDigest(ALGORITHM);
	}

	/**
	 * 获取摘要的十六进制字符串
	 * @param data
	 * @return 摘要的十六进制字符串
	 */
	public static String getHexString(byte[] data) {
		return Hex.encodeHexString(data);
	}
	
   public static String getBytesBASE64(byte[] data) {
        return Base64.getBASE64(data);
    }
   
   	public static byte[] getBytesBASE64(String data){
   		return Base64.getBytesBASE64(data);
   	}
   	
   	public static String getKey(){
   		return SignUtils.KEY;
   	}
   
   
   /** 
    * 除去数组中的空值和签名参数
    * @param sArray 签名参数组
    * @return 去掉空值与签名参数后的新签名参数组
    */
   public static Map<String, String> paraFilter(Map<String, String> sArray) {

       Map<String, String> result = new HashMap<String, String>();

       if (sArray == null || sArray.size() <= 0) {
           return result;
       }

       for (String key : sArray.keySet()) {
           String value = sArray.get(key);
           if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
               || key.equalsIgnoreCase("sign_type")) {
               continue;
           }
           result.put(key, value);
       }

       return result;
   }

   /** 
    * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
    * @param params 需要排序并参与字符拼接的参数组
    * @return 拼接后字符串
    */
   public static String createLinkString(Map<String, String> params) {

       List<String> keys = new ArrayList<String>(params.keySet());
       Collections.sort(keys);

       String prestr = "";

       for (int i = 0; i < keys.size(); i++) {
           String key = keys.get(i);
           String value = params.get(key);
           
           try {
        	   if(CharUtil.isChinese(value)){
        		   value = URLEncoder.encode(value, "UTF-8");
        	   }
	   		} catch (UnsupportedEncodingException e) {
	   			System.out.println(e.getMessage());
	   		}           

           if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
               prestr = prestr + key + "=" + value;
           } else {
               prestr = prestr + key + "=" + value + "&";
           }
       }

       return prestr;
   }
   
   /**
    * 生成签名
    * @Title: createSign 
    * @param paramMap
    * @return
    * @date 2016年12月24日 下午5:22:45  
    * @author xiongbin
    */
   public static String createSign(Map<String,String> paramMap){
	   //1.对请求参数过滤，过滤掉sign_type和sign字段内容
   		paramMap = SignUtils.paraFilter(paramMap);
   		//2.生成带签名字符串
	    String s =  SignUtils.createLinkString(paramMap);
	    
	    //3.加上key
	    s = s + SignUtils.getKey();
	    
	    byte[] shaByte = SignUtils.getSha512Digest().digest(s.getBytes());
	    
	    String ed = SignUtils.getHexString(shaByte);
	    
	    
	    return ed;
   }
   
	

	
}