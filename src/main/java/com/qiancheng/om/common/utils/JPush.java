package com.qiancheng.om.common.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;

public class JPush {

    private static final Logger LOGGER = Logger.getLogger(JPush.class);
    private static final PropertiesHandler PROP = new PropertiesHandler("order_meal.properties");
    private static String MASTER_SECRET = PROP.getValue("jpush.masterSecret");
    private static String APP_KEY = PROP.getValue("jpush.appKey");

    /**
     * 构建推送平台
     *
     * @param alias   设备别名
     * @param dataMap 推送消息
     * @return 创建好的推送对象
     */
    private static PushPayload buildPushObjectAllAliasAlert(String alias, Map<String, String> dataMap) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android("", null, dataMap))
                .build();
    }

    /**
     * 推送信息
     *
     * @param alias   设备别名
     * @param dataMap 推送消息
     */
    public static void push(String alias, Map<String, String> dataMap) {
//        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig
//                .getInstance());
//
//        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = buildPushObjectAllAliasAlert(alias, dataMap);
//
//        try {
//            PushResult result = jpushClient.sendPush(payload);
//            LOGGER.info("Got result - " + result);
//
//        } catch (APIConnectionException e) {
//            LOGGER.error("Connection error, should retry later", e);
//
//        } catch (APIRequestException e) {
//            // 忽略列表
//            switch (e.getErrorCode()) {
//                case 1011:
//                    return;
//            }
//
//            LOGGER.error("Should review the error, and fix the request", e);
//            LOGGER.info("HTTP Status: " + e.getStatus());
//            LOGGER.info("Error Code: " + e.getErrorCode());
//            LOGGER.info("Error Message: " + e.getErrorMessage());
//        }
    }

}
