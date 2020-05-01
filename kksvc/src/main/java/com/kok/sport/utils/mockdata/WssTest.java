package com.kok.sport.utils.mockdata;

import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.handshake.ServerHandshake;
public class WssTest {
	//https://www.nydmu.com/
    public static void main(String[] args) throws URISyntaxException, InterruptedException {
       String string = "wss://112.121.163.125:8888";
     //  string="wss://www.nydmu.com:8888";
	new SSLWebSocketClient(new URI(string),"初始化消息") {
            @Override
            public void onClose(int arg0, String arg1, boolean arg2) {
                System.out.println("onClose");
            }
            @Override
            public void onError(Exception arg0) {
                System.out.println("onError");
            }
            @Override
            public void onMessage(String arg0) {
                System.out.println("onMessage");
                System.out.println(arg0);
              //  this.send(arg0);
            }
            @Override
            public void onOpen(ServerHandshake arg0) {
                System.out.println("onOpen");
                this.send("发送消息");
            }}.connect();
//            while(true)
//            {
//            	Thread.sleep(3000);
//            }
    }
}