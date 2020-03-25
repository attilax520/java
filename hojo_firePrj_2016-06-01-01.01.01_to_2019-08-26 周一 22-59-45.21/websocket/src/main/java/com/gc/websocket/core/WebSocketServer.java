package com.gc.websocket.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shenhuaming
 * Date 2019/8/6
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);
    //计数器
    private static AtomicInteger count = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        LOGGER.info("有新连接加入，当前连接数为" + count.incrementAndGet());
    }

    @OnClose
    public void onCLose() {
        webSocketSet.remove(this);
        LOGGER.info("有连接关闭，当前连接数为" + count.decrementAndGet());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.info(("来自客户端的消息:" + message));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        LOGGER.info("WebSocket错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.info("消息发送错误:"+ex.getMessage());
        }
    }

    /**
     * 群发
     */
    public static void sendInfo(String message) {
        LOGGER.info("群发：" + message);
        for (WebSocketServer item : webSocketSet) {
            item.sendMessage(message);
        }
    }
}
