package com.insigma.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;


/**
 * java后台极光推送方式二：使用Java SDK
 */
@SuppressWarnings({ "deprecation", "restriction" })
public class JiguangPush {
    private static final Logger log = LoggerFactory.getLogger(JiguangPush.class);
    private static String appKey = "b5f52f2280c71af8e89bf708";
    private static String masterSecret = "43f2ff624684f95fcd8bd039";
    private static final String ALERT = "推送信息";
    /**
     * 极光推送
     */
    public void jiguangPush(){
        String alias = "123456";//声明别名
        System.out.println("对别名" + alias + "的用户推送信息");
        PushResult result = push_all(ALERT);
        if(result != null && result.isResultOK()){
            System.out.println("针对别名" + alias + "的信息推送成功！");
        }else{
            System.out.println("针对别名" + alias + "的信息推送失败！");
        }
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_alias_alert(String alias,String alert){
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

    /**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_alert(String alert){
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                //指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration id
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }
    /**
     * 极光推送方法(采用java SDK)
     * @param alias
     * @param alert
     * @return PushResult
     */
    public static PushResult push(String alias,String alert){
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        PushPayload payload = buildPushObject_android_ios_alias_alert(alias,alert);
        try {
            return jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            System.out.println("Connection error. Should retry later. "+ e);
            return null;
        } catch (APIRequestException e) {
            System.out.println("Error response from JPush server. Should review and fix it. "+ e);
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());
            System.out.println("Msg ID: " + e.getMsgId());
            return null;
        }
    }


    /**
     * 极光推送方法(采用java SDK)
     * @param alert
     * @return PushResult
     */
    public static PushResult push_all(String alert){
        ClientConfig clientConfig = ClientConfig.getInstance();
        JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
        PushPayload payload = buildPushObject_android_ios_alert(alert);
        try {
            return jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            System.out.println("Connection error. Should retry later. "+ e);
            return null;
        } catch (APIRequestException e) {
            System.out.println("Error response from JPush server. Should review and fix it. "+ e);
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Error Message: " + e.getErrorMessage());
            System.out.println("Msg ID: " + e.getMsgId());
            return null;
        }
    }


    public static void main(String [] args){
        JiguangPush jiguangPush=new JiguangPush();
        jiguangPush.jiguangPush();
    }
}
