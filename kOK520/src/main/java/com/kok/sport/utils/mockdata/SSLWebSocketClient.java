package com.kok.sport.utils.mockdata;

import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
/**
 * 构建 SSLWebSocket客户端，忽略证书
 */
public abstract class SSLWebSocketClient extends WebSocketClient {
    //构造方法
    public SSLWebSocketClient(URI serverURI,String message) {
        super(serverURI);
        if(serverURI.toString().contains("wss://")){
            trustAllHosts(this);
          //   this.send(message);
        }
    }
    public SSLWebSocketClient(URI serverURI,Draft draft) {
        super(serverURI,draft);
        if(serverURI.toString().contains("wss://"))
            trustAllHosts(this);
    }
    /**
     * 忽略证书
     * @param client
     */
    static void trustAllHosts(SSLWebSocketClient client) {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }
        }};
        try {
            SSLContext ssl = SSLContext.getInstance("TLS");
            ssl.init(null, trustAllCerts, new java.security.SecureRandom());
            //impt
            client.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(ssl));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
