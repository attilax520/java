package com.kok.sport.utils.mockdata;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
public class WssTest {
	//https://www.nydmu.com/
    public static void main(String[] args) throws  Exception {
    	String f="D:\\prj\\sport-service\\kok-sport-service\\src\\main\\java\\com\\kok\\sport\\utils\\mockdata\\testsend.json";
    	String t=FileUtils.readFileToString(new File(f));
     
       
       	String msg="ttt";
       	Map msgMapCore=Maps.newLinkedHashMap();
       	msgMapCore.put("name", "atiii");
    	Map msgMap=Maps.newLinkedHashMap();
    	msgMap.put("method", "testsend");
    	msgMap.put("msg", msgMapCore);;
    	msg=JSON.toJSONString(msgMap);
    	System.out.println(msg);
      //  sendMsg(msg);
    	//openTstSendMsg ,hostip
    	WssTest.conn("xxx",hostip);
//            while(true)
//            {
//            	Thread.sleep(3000);
//            }
    }
    
   public static  String hostip = "wss://112.121.163.125:8888";
 //   public static  String hostip = "wss://localhost:8888";
@Deprecated
    public static void sendMsg(String msg) throws URISyntaxException {
	//	String string = "wss://112.121.163.125:8888";
		 // string = "wss://127.0.0.1:8888";
		sendMsg(msg, hostip);
	//sslWebSocketClient.send();
	}

	public static void sendMsgClose(String msg, String host) throws URISyntaxException {
		System.out.println(host);
		// string = "wss://127.0.0.1:8888";
    //    string="wss://8888.nydmu.com";
	SSLWebSocketClient sslWebSocketClient = new SSLWebSocketClient(new URI(host),"初始化消息") {
            @Override
            public void onClose(int arg0, String arg1, boolean arg2) {
                System.out.println("onClose");
            }
            @Override
            public void onError(Exception arg0) {
                System.out.println("onError");
                System.out.println(arg0);
            }
            @Override
            public void onMessage(String arg0) {
                System.out.println("onMessage recv");
                System.out.println(arg0);
              //  this.send(arg0);
            }
            @Override
            public void onOpen(ServerHandshake arg0) {
                System.out.println("onOpen");
                 this.send(msg);
            }};
	sslWebSocketClient.connect();
//	sslWebSocketClient.close();cant receive msg ,maybe it async process
	//must cant close ,beirs 
System.out.println("sendms g....");
	}
    
    public static void conn(String openTstSendMsg, String host) throws URISyntaxException {
		System.out.println(host);
		// string = "wss://127.0.0.1:8888";
    //    string="wss://8888.nydmu.com";
	SSLWebSocketClient sslWebSocketClient = new SSLWebSocketClient(new URI(host),"初始化消息") {
            @Override
            public void onClose(int arg0, String arg1, boolean arg2) {
                System.out.println("onClose");
            }
            @Override
            public void onError(Exception arg0) {
                System.out.println("onError");
                System.out.println(arg0);
            }
            @Override
            public void onMessage(String arg0) {
                System.out.println("onMessage recv");
                System.out.println(arg0);
              //  this.send(arg0);
            }
            @Override
            public void onOpen(ServerHandshake arg0) {
                System.out.println("onOpen");
                 this.send(openTstSendMsg);
            }};
	sslWebSocketClient.connect();
System.out.println("sendms g....");
	}
	private static void sendMsg(String msg, String host) throws URISyntaxException {
		System.out.println(host);
		// string = "wss://127.0.0.1:8888";
    //    string="wss://8888.nydmu.com";
	SSLWebSocketClient sslWebSocketClient = new SSLWebSocketClient(new URI(host),"初始化消息") {
            @Override
            public void onClose(int arg0, String arg1, boolean arg2) {
                System.out.println("onClose");
            }
            @Override
            public void onError(Exception arg0) {
                System.out.println("onError");
                System.out.println(arg0);
            }
            @Override
            public void onMessage(String arg0) {
                System.out.println("onMessage recv");
                System.out.println(arg0);
              //  this.send(arg0);
            }
            @Override
            public void onOpen(ServerHandshake arg0) {
                System.out.println("onOpen");
                 this.send(msg);
            }};
	sslWebSocketClient.connect();
System.out.println("sendms g....");
	}
}