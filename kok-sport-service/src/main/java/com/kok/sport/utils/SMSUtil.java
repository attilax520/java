package com.kok.sport.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SMSUtil {

    private static final String addr = "http://api.sms.cn/sms/";
    private static final String userId = "eric191021";
    private static final String pwd = "Eric1021";
    private static final String encode = "utf8";
    private static final String product = "先锋体育";
    private static final String registerTemplateId = "532470";//注册模板
    private static final String loginTemplateId = "532553";//登录模板
    private static final String retrieveTemplateId = "532554";//找回密码模板

    public static String send(String code, String mobile, String type) throws Exception {
        String result = "";
        switch (type) {
            case "1":
                result = sendRequest(code, mobile, registerTemplateId);
                break;
            case "2":
                result = sendRequest(code, mobile, loginTemplateId);
                break;
            case "3":
                result = sendRequest(code, mobile, retrieveTemplateId);
                break;
        }
        return result;
    }

    private static String sendRequest(String code, String mobile, String templateId) throws Exception {
        //密码进行md5 32位加密
        String md532 = MD5.encoderByMd532(pwd + userId);

        //组建请求
        String straddr = addr +
                "?ac=send&template=" + templateId +
                "&uid=" + userId +
                "&pwd=" + md532 +
                "&mobile=" + mobile +
                "&encode=" + encode +
                "&content={\"code\":\"" + code +"\"}";

        StringBuffer sb = new StringBuffer(straddr);
        System.out.println("URL:" + sb);

        //发送请求
        URL url = new URL(sb.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        //返回结果
        String inputline = in.readLine();
        System.out.println("Response:" + inputline);
        return inputline;
    }
}
