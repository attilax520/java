//package com.kok.sport.utils.mockdata;
//
//import org.java_websocket.enums.ReadyState;
//
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManagerFactory;
//import java.io.*;
//import java.net.URI;
//import java.security.KeyStore;
//
//class SSLClientExample {
//
//    public static void main( String[] args ) throws Exception {
//        WebSocketClientInst chatclient = new WebSocketClientInst( new URI( "wss://ip:port/test/path" ) );
//
//        // load up the key store
//        String KEYSTORE = "xx/xxxx/my.store";  //基于证书生成的store秘钥文件的路径
//        String STOREPASSWORD = "123456";  //使用keytool工具时，输入的密码
//        String KEYPASSWORD = "123456";
//
//        KeyStore ks = KeyStore.getInstance( KeyStore.getDefaultType() );
//        File kf = new File( KEYSTORE );
//        ks.load( new FileInputStream( kf ), STOREPASSWORD.toCharArray() );
//
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance( "SunX509" );
//
//        kmf.init( ks, KEYPASSWORD.toCharArray() );
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
//        tmf.init( ks );
//
//        SSLContext sslContext = null;
//        sslContext = SSLContext.getInstance( "TLS" );
//        sslContext.init( kmf.getKeyManagers(), tmf.getTrustManagers(), null );
//
//        SSLSocketFactory factory = sslContext.getSocketFactory();// (SSLSocketFactory) SSLSocketFactory.getDefault();
//
//        chatclient.setSocketFactory( factory );
//
//        chatclient.connectBlocking();
//
//        boolean loop = true;
//        int times = 0;
//        while (loop){
//            times++;
//            if (ReadyState.OPEN.equals(chatclient.getReadyState())) {
//                chatclient.send(getBytes("a.wav"));  //发送二进制文件
//            }else {
//                System.out.println("还没ready, 继续进行中");
//                if (times<=10) {
//                    Thread.sleep(1000);
//                }else{
//                    System.out.println("超时"); 
//                    break;
//                }
//            }
//        }
//    }
//
//    public static byte[] getBytes(String filePath){
//        byte[] buffer = null;
//        try {
//            File file = new File(filePath);
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
//            byte[] b = new byte[1000];
//            int n;
//            while ((n = fis.read(b)) != -1) {
//                bos.write(b, 0, n);
//            }
//            fis.close();
//            bos.close();
//            buffer = bos.toByteArray();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return buffer;
//    }
//}
// 