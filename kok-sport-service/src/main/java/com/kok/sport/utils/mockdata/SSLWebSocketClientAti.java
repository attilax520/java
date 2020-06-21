package com.kok.sport.utils.mockdata;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

/**
 * 构建 SSLWebSocket客户端，忽略证书
 */
public abstract class SSLWebSocketClientAti  {

	public static SSLWebSocketClient curLicent;

	 
	/**
	 * 忽略证书
	 * 
	 * @param client
	 */
	static void trustAllHosts(SSLWebSocketClient client) {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			}
		} };
		try {
			SSLContext ssl = SSLContext.getInstance("TLS");
			ssl.init(null, trustAllCerts, new java.security.SecureRandom());
			// impt
			client.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(ssl));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 
	public static void ini(String host) throws InterruptedException, URISyntaxException {
		System.out.println(host);
		 

			curLicent = new SSLWebSocketClient(new URI(host), "初始化消息") {
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
					System.out.println("-onMessage recv");
					System.out.println(arg0);
					// this.send(arg0);
				}

				@Override
				public void onOpen(ServerHandshake arg0) {
					System.out.println("onOpen" + arg0);
					// this.send(msg);

				}
			};

			curLicent.connectBlocking();
		 
	}

//	public static void ini(String host) {
//		System.out.println(host);
//		if (SSLWebSocketClient.sslWebSocketClient == null) {
//			try {
//				sslWebSocketClient = new SSLWebSocketClient(new URI(host), "初始化消息") {
//					@Override
//					public void onClose(int arg0, String arg1, boolean arg2) {
//						System.out.println("onClose");
//					}
//
//					@Override
//					public void onError(Exception arg0) {
//						System.out.println("onError");
//						System.out.println(arg0);
//					}
//
//					@Override
//					public void onMessage(String arg0) {
//						System.out.println("-onMessage recv");
//						System.out.println(arg0);
//						// this.send(arg0);
//					}
//
//					@Override
//					public void onOpen(ServerHandshake arg0) {
//						System.out.println("onOpen" + arg0);
//						// this.send(msg);
//
//					}
//				};
//			 
//				sslWebSocketClient.connectBlocking();
//			} catch ( Exception e) {
//				throw new RuntimeException(e);
//			}
//
//		}
//	}

	public static void send(String msg, String hostip4jincyo)  {
		if (SSLWebSocketClientAti.curLicent == null) {
			try {
				ini(hostip4jincyo);
			} catch (InterruptedException | URISyntaxException e) {
				  throw new RuntimeException(e);
			}
		}
		
		
		 try {
			 curLicent.send(msg);
		} catch (WebsocketNotConnectedException e) {
			try {
				ini(hostip4jincyo);
			} catch (  URISyntaxException e2) {
				  throw new RuntimeException(e2);
			}catch (InterruptedException e2) {
				  throw new RuntimeException(e2);
			}
			 curLicent.send(msg);
		}
		

	}
}
