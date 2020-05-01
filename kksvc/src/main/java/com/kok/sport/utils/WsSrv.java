package com.kok.sport.utils;

import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kok.SportApplication;
import com.kok.sport.utils.db.JsonUtil;
import com.kok.sport.utils.db.MybatisQueryUtil;
import com.kok.sport.utils.db.MybatisUtil;

//   /bookmarksHtmlEverythingIndexPrj/src/awebsocket/WsServer.java
public class WsSrv extends WebSocketServer {
//	static {
//		timerEvent() ;
//	}
//	

	public static void main(String args[]) {
		WebSocketImpl.DEBUG = true;
		// WebSocketImpl.
		int port = 5202; // 端口
		WsSrv WsServer1 = new WsSrv(port);
		WsServer1.start();

		System.out.println("--");
	}

	public static void startWebsocketService(int port) {

		try {
			WebSocketImpl.DEBUG = true;
			// int port = 5202; // 端口
			WsSrv WsServer1 = new WsSrv(port);
			WsServer1.start();

			System.out.println("--");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public WsSrv(int port) {
		super(new InetSocketAddress(port));
	}

	public WsSrv(InetSocketAddress address) {
		super(address);
	}

	public static List<WebSocket> conns_li = Lists.newArrayList();

	public static void timerEvent() {
		System.out.println("timerEvent");
		Timer tmr = new Timer();
		tmr.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					System.out.println("timerEvent run");

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, 1, 5000);

	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		// ws连接的时候触发的代码，onOpen中我们不做任何操作
		// conn.send("open ok..");
		if (!conns_li.contains(conn))
			conns_li.add(conn);
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		// 断开连接时候触发代码
		// userLeave(conn);
		try {
			conns_li.remove(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(reason);
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
//		if(!conns_li.contains(conn))
//		conns_li.add(conn);
		try {
			JSONObject jo = JSONObject.parseObject(message);
			if (jo.getString("method").equals("testsend")) {
				notifyWebsocket(jo.getString("msg"));
			}

			else if (jo.getString("method").equals("conncount")) {
				LinkedHashMap<String, Object> m = Maps.newLinkedHashMap();
				m.put("WsSrv.conns_li.size()", WsSrv.conns_li.size());
				conn.send(JSON.toJSONString(m));
			}

		} catch (Exception e) {
			e.printStackTrace();
			conn.send(JSON.toJSONString(e));
		}

		System.out.println(message);
		// conn.send("ok..");
//        if(null != message &&message.startsWith("online")){
//            String userName=message.replaceFirst("online", message);//用户名
//            userJoin(conn,userName);//用户加入
//        }else if(null != message && message.startsWith("offline")){
//            userLeave(conn);
//        }

	}

	public Object notifyWebsocket(String notifyMsg) throws Exception {

//		LinkedHashMap<String, Object> m = Maps.newLinkedHashMap();
//		m.put("WsSrv.conns_li.size()", WsSrv.conns_li.size());
//		m.put("notifyMsg", JsonUtil.tojsonIfjsonstr(notifyMsg) );
		WsSrv.conns_li.forEach(new Consumer<WebSocket>() {

			@Override
			public void accept(WebSocket t) {
				try {

					t.send(notifyMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		// return m;
		// return JSON.toJSONString(m, true);
		return notifyMsg;

	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		// 错误时候触发的代码
		System.out.println("on error");
		ex.printStackTrace();
	}

	/**
	 * 去除掉失效的websocket链接
	 * 
	 * @param conn
	 */
	private void userLeave(WebSocket conn) {
		// WsPool.removeUser(conn);
	}

	/**
	 * 将websocket加入用户池
	 * 
	 * @param conn
	 * @param userName
	 */
	private void userJoin(WebSocket conn, String userName) {
		// WsPool.addUser(userName, conn);
	}

}