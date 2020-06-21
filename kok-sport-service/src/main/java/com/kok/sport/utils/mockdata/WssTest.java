package com.kok.sport.utils.mockdata;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.kok.sport.utils.CaptchData;
import com.kok.sport.utils.SpringUtil;

import brave.internal.Lists;

public class WssTest {
	// https://www.nydmu.com/
	//-Dhostip4jincyo=112.121.163.125:8886
	//-Dhostip=112.121.163.125:8888
	//-Dhostip=localhost:8888
	public static String hostip4jincyo=getWss();

	private static String getWss() {
		
		if(System.getProperty("hostip4jincyo")!=null)
		{
			return "wss://"+System.getProperty("hostip4jincyo").trim();
		}
		return "wss://"+SpringUtil.getCfgPropertySafe("wss").toString();
		
		
		//return "wss://"+System.getProperty("hostip4jincyo","112.121.163.125:8888")+"";
	}
	  public static String hostip = getWss();
			  // "wss://"+System.getProperty("hostip","112.121.163.125:8888") ;  //for  list tonzhi
	//public static String hostip = "wss://localhost:8888";

	public static void main(String[] args) throws Exception {
		String f = "D:\\prj\\sport-service\\kok-sport-service\\src\\main\\java\\com\\kok\\sport\\utils\\mockdata\\testsend.json";
		String t = FileUtils.readFileToString(new File(f));

		String msg = "ttt";
		Map msgMapCore = Maps.newLinkedHashMap();
		msgMapCore.put("name", "atiii");
		Map msgMap = Maps.newLinkedHashMap();
		msgMap.put("method", "testsend");
		msgMap.put("msg", msgMapCore);
		;
		msg = JSON.toJSONString(msgMap);
		System.out.println(msg);
		// sendMsg(msg);
		// openTstSendMsg ,hostip
	 	WssTest.conn("xxx", hostip4jincyo);
//            while(true)
//            {
//            	Thread.sleep(3000);
//            }
		
		
		
	}

	@Deprecated
	public static void sendMsg(String msg) throws URISyntaxException {
		// String string = "wss://112.121.163.125:8888";
		// string = "wss://127.0.0.1:8888";
		sendMsg(msg, hostip);
		// sslWebSocketClient.send();
	}
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger("wss conn");
	//public static int a2=timr();
	public static List<SSLWebSocketClient> connList = com.google.common.collect.Lists.newArrayList();
	public static List<SSLWebSocketClient> connList_dels_wait = com.google.common.collect.Lists.newArrayList();
	
//	public static int a=timr();
	public static  int timr()  {
		//gc close
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("--run::"+new Date());
			//	List<SSLWebSocketClient> connList;
				for (SSLWebSocketClient sslWebSocketClient2 : connList) {
					try {
						sslWebSocketClient2.close();
						//sslWebSocketClient2.
					} catch (Exception e) {
						e.printStackTrace();
					}
					connList_dels_wait.add(sslWebSocketClient2);
				
					System.out.println(new Date());
				}
				for (SSLWebSocketClient sslWebSocketClient2 : connList_dels_wait) {
					 
					 try {
						 connList.remove(sslWebSocketClient2);	
					} catch (Exception e) {
						e.printStackTrace();
					}
					 
				}
				connList_dels_wait.clear();			
			}
		};
		
		
		int a=33;
		while(a>1) {
			timerTask.run();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				 
				e.printStackTrace();
			}
 
		}
	//	timer.schedule(timerTask, 0,2000);
	 	return 1;
	  
 };
 public static  int timr2(){
		//gc close
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("--run::"+new Date());
			//	List<SSLWebSocketClient> connList;
				for (SSLWebSocketClient sslWebSocketClient2 : connList) {
					try {
						sslWebSocketClient2.close();
						//sslWebSocketClient2.
					} catch (Exception e) {
						e.printStackTrace();
					}
					connList_dels_wait.add(sslWebSocketClient2);
				
					System.out.println(new Date());
				}
				for (SSLWebSocketClient sslWebSocketClient2 : connList_dels_wait) {
					 try {
						 connList.remove(sslWebSocketClient2);	
					} catch (Exception e) {
						e.printStackTrace();
					}
							
					 
				}
				connList_dels_wait.clear();			
			}
		}, 0,3000);
		return 1;
	//	timer.
};




	public static void sendMsgClose(String msg, String host) throws URISyntaxException {
		System.out.println(host);
		// string = "wss://127.0.0.1:8888";
		// string="wss://8888.nydmu.com";
		 	
		SSLWebSocketClientAti.send(msg,host);
			// sslWebSocketClient.close();
		 
		 

	// 	connList.add(sslWebSocketClient);

		//
		// cant receive msg ,maybe it async process
		// must cant close ,beirs
		System.out.println("sendms ok....");
	}

	public static void conn(String openTstSendMsg, String host) throws URISyntaxException {
		System.out.println(host);
		// string = "wss://127.0.0.1:8888";
		// string="wss://8888.nydmu.com";
		SSLWebSocketClient sslWebSocketClient = new SSLWebSocketClient(new URI(host), "初始化消息") {
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
				
				logger.info("onMessage recv"+arg0);
				System.out.println(arg0);
				// this.send(arg0);
			}

			@Override
			public void onOpen(ServerHandshake arg0) {
				System.out.println("onOpen");
				this.send(openTstSendMsg);
			}
		};
		sslWebSocketClient.connect();
		System.out.println("==connect ok....");
	}

	private static void sendMsg(String msg, String host) throws URISyntaxException {
		System.out.println(host);
		// string = "wss://127.0.0.1:8888";
		// string="wss://8888.nydmu.com";
		SSLWebSocketClient sslWebSocketClient = new SSLWebSocketClient(new URI(host), "初始化消息") {
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
				// this.send(arg0);
			}

			@Override
			public void onOpen(ServerHandshake arg0) {
				System.out.println("onOpen");
				this.send(msg);
			}
		};
		sslWebSocketClient.connect();
		System.out.println("sendms g....");
	}
}