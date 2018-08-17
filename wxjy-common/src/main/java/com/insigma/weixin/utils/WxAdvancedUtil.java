package com.insigma.weixin.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.insigma.common.listener.AppConfig;
import com.insigma.weixin.pojo.AccessToken;
import com.insigma.weixin.pojo.SNSUserInfo;
import com.insigma.weixin.pojo.WeixinOauth2Token;
import com.insigma.weixin.pojo.WeixinUserInfo;


/**
 * 微信api调用工具类
 * @author 
 *
 */
public class WxAdvancedUtil {
	
	private static Logger log = LoggerFactory.getLogger(WxAdvancedUtil.class);
	
	public static final String APPID = AppConfig.getProperties("weixin_appid");
	public static final String APPSECRET = AppConfig.getProperties("weixin_appsecret");

    // 凭证获取（GET）
    //获取acces_token 接口地址
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //上传文件url接口地址
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    /**
	   * 网页授权获取openId
	   * @Title: getOpenId 
	   * @Description: TODO
	   * @param code
	   * @return JSONObject
	   */
	public static JSONObject getOpenId(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		String requestUrl = url.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		return jsonObject;
	}

    /**
     * 下载媒体文件
     *
     * @param mediaId 媒体文件标识
     * @param savePath 文件在服务器上的存储路径
     * @param token 接口访问凭证
     * @return
     */
    public static String getMedia(String mediaId, String savePath, String token) {
        String filePath = null;
        // 拼接请求地址
        String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", token).replace("MEDIA_ID", mediaId);
        System.out.println(requestUrl);
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");

            if (!savePath.endsWith("/")) {
                savePath += "/";
            }
            // 根据内容类型获取扩展名
            String fileExt = getFileExt(conn.getHeaderField("Content-Type"));
            // 将mediaId作为文件名
            filePath = savePath + mediaId + fileExt;

            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1)
                fos.write(buf, 0, size);
            fos.close();
            bis.close();

            conn.disconnect();

            System.out.println("下载媒体文件成功，filePath=" + filePath);
        } catch (Exception e) {
            filePath = null;
            System.out.println("下载媒体文件失败：{}\n" + e);
        }
        return filePath;
    }

    /** 
     *  
     * @Description: 获取access_token 
     * @param appid  
     * @param appsecret  
     * @Title: getAccessToken 
     * @return AccessToken     
     * @date: 2015年9月8日 下午2:23:01 
     * @author heboy 
     */  
    public static AccessToken getAccessToken(String appid, String appsecret) {  
        AccessToken accessToken = null;  
        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);  
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
        // 如果请求成功  
        if (null != jsonObject) {  
            try {  
                accessToken = new AccessToken();  
                accessToken.setAccessToken(jsonObject.getString("access_token"));  
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
            } catch (JSONException e) {  
                accessToken = null;  
                // 获取token失败  
                log.info("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
        return accessToken;  
    } 

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType   内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType))
            fileExt = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileExt = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileExt = ".amr";
        else if ("video/mp4".equals(contentType))
            fileExt = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileExt = ".mp4";
        return fileExt;
    }

    /**
     * 发送https请求
     *
     * @param requestUrl 请求地址
     * @param requestMethod  请求方式（GET、POST）
     * @param outputStr  提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}" + ce);
        } catch (Exception e) {
            System.out.println("https请求异常：{}" + e);
        }
        return jsonObject;
    }


    /**
     * 获取用户信息
     *
     * @param accessToken 接口访问凭证 用户标识
     * @return WeixinUserInfo
     */
    public static WeixinUserInfo getUserInfo(String accessToken,String openId) {
        WeixinUserInfo weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        // System.out.println("openId:"+openId);
        if (null != jsonObject) {
            try {
                weixinUserInfo = new WeixinUserInfo();
                // 用户的标识
                weixinUserInfo.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUserInfo.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.setCity(jsonObject.getString("codetype"));
                // 用户的语言，简体中文为zh_CN
                weixinUserInfo.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
            } catch (Exception e) {
                if (0 == weixinUserInfo.getSubscribe()) {
                    System.out.println("用户{" + weixinUserInfo.getOpenId() + "}已取消关注" + e);
                } else {
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    System.out.println("获取用户信息失败 errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}" + e);
                    e.printStackTrace();
                }
            }
        }
        return weixinUserInfo;
    }

    /**
     * 获取网页授权凭证
     *
     * @param appId  公众账号的唯一标识
     * @param appSecret   公众账号的密钥
     * @param code
     * @return WeixinAouth2Token
     */
    public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
        // 获取网页授权凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                System.out.println("获取用户信息失败 errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}" + e);
                e.printStackTrace();
            }
        }
        return wat;
    }

    /**
     * 刷新网页授权凭证
     *
     * @param appId 公众账号的唯一标识
     * @param refreshToken
     * @return WeixinAouth2Token
     */
    public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
        // 刷新网页授权凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                System.out.println("获取用户信息失败 errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}" + e);
                e.printStackTrace();
            }
        }
        return wat;
    }

    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken  网页授权接口调用凭证
        用户标识
     * @return SNSUserInfo
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
    public static SNSUserInfo getSNSUserInfo(String accessToken,String openId) {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("codetype"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                System.out.println("获取用户信息失败 errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}" + e);
                e.printStackTrace();
            }
        }
        return snsUserInfo;
    }

    /**
     * 发送客服消息
     *
     * @param accessToken
     *            接口访问凭证
     * @param jsonMsg
     *            json格式的客服消息（包括touser、msgtype和消息内容）
     * @return true | false
     */
    public static boolean sendCustomMessage(String accessToken, String jsonMsg) {
        System.out.println("消息内容：{}" + jsonMsg);
        boolean result = false;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        // 发送客服消息
        JSONObject jsonObject = httpsRequest(requestUrl, "POST", jsonMsg);

        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
                System.out.println("客服消息发送成功 errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}");
            } else {
                System.out.println("客服消息发送失败 errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}");
            }
        }

        return result;
    }

    /**
     * 组装文本客服消息
     *
     
     *            消息发送对象
     * @param content
     *            文本消息内容
     * @return
     */
    public static String makeTextCustomMessage(String content,String openId) {
        // 对消息内容中的双引号进行转义
        content = content.replace("\"", "\\\"");
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
        return String.format(jsonMsg, openId, content);
    }

    /**
     * 组装图片客服消息
     *
     
     *            消息发送对象
     * @param mediaId
     *            媒体文件id
     * @return
     */
    public static String makeImageCustomMessage(String mediaId,String openId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId);
    }

    /**
     * 获取ticket，用于微信JS
     *
     * @param accessToken
     * @return
     */
    public static String getTicket(String accessToken) {
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        // 发送
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        String result = null;
        if (null != jsonObject) {
            int errorCode = jsonObject.getInt("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = jsonObject.getString("ticket");
                System.out.println("获取ticket成功 errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}");
            } else {
                System.out.println("获取ticket失败 errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}");
            }
        }
        return result;
    }

    /**
     * 生成永久二维码
     *
     * @param accessToken
     * @param action_name
     * @param scene_id
     * @return
     */
    public static String getQRCodeTicket(String accessToken, String action_name, int scene_id) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
        requestUrl = requestUrl.replace("TOKEN", accessToken);
        // 发送
        String param = "{\"action_name\": " + action_name + ", \"action_info\": {\"scene\": {\"scene_id\": " + scene_id
                + "}}}";
        JSONObject jsonObject = httpsRequest(requestUrl, "POST", param);
        String result = null;
        if (null != jsonObject) {
            result = jsonObject.getString("ticket");
        }
        return result;
    }
    
    /** 
     * 发起https请求并获取结果 
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
            if ("GET".equalsIgnoreCase(requestMethod)){  
                httpUrlConn.connect();  
            }
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    } 
  
    
    /**
     * URL编码（utf-8）
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
      String result = source;
      try {
        result = java.net.URLEncoder.encode(source, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      return result;
    }
    /**
     * 获取URL  
     * @param url
     * @return
     */
    public static String getUrl(String url){
        String result = null;
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);
            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result= EntityUtils.toString(response.getEntity(),"UTF-8");
            } 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * Get请求
     * @param url
     * @return
     * 注意事项 添加jsonobject的jar包及依赖jar包
     *       添加httpclient，httpcore的 jar包
     */
    public static JSONObject doGetStr(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try{
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String result = EntityUtils.toString(entity,"UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    /**
     * post请求
     * @param url
     * @param outStr
     * @return
     */
    public static JSONObject doPostStr(String url,String outStr){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try{
            httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(),"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    /**
     * 获取access_token
     * @return
     */
    public static AccessToken getAccessToken(){
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = doGetStr(url);
        if(jsonObject != null){
            token.setAccessToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        System.err.println(jsonObject);
        return token;
    }
    
    /**
     * 文件上传
     * @param filePath 路径
     * @param accessToken
     * @param type 类型
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }
        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("POST"); 
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); 
        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);
        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        JSONObject jsonObj = JSONObject.fromObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        if(!"image".equals(type)){
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        System.err.println("----"+mediaId);
        return mediaId;
    }

}
