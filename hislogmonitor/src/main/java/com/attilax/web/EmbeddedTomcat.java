package com.attilax.web;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.ClassLoaderFactory;
import org.apache.catalina.startup.Tomcat;

import com.attilax.io.pathx;
import com.attilax.util.Global;

import javax.servlet.ServletException;

/**
 * 
 * http://localhost/cloud/clinewadvice/clinicvaf1insert?lver=22
 * 
 * Created by nil on 2014/8/1.
 */
public class EmbeddedTomcat {
    private Tomcat tomcat;    
    static int port = 80;

    private void startTomcat(int port, String contextPath, String baseDir)
            throws ServletException, LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(".");
        StandardServer server = (StandardServer) tomcat.getServer();
        AprLifecycleListener listener = new AprLifecycleListener();
        server.addLifecycleListener(listener);
        tomcat.addWebapp(contextPath, baseDir);
        tomcat.start();
    }

    private void stopTomcat() throws LifecycleException {
        tomcat.stop();
    }

    public static void main(String[] args) {
    //	org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher
    //	org.jboss.resteasy.plugins.providers.multipart.MimeMultipartProvider
    	System.out.println("");
        try {
           
            String contextPath = "/";
            String baseDir =pathx.webAppPath_jensyegeor();
            System.out.println("basedir:"+baseDir);
            		//new File("webroot").getAbsolutePath();  // 项目中web目录名称，以前版本为WebRoot、webapp、webapps，现在为WebContent
            EmbeddedTomcat EmbeddedTomcat1 = new EmbeddedTomcat();
            EmbeddedTomcat1.startTomcat(port, contextPath, baseDir);
            // 由于Tomcat的start方法为非阻塞方法,加一个线程睡眠模拟线程阻塞.
            
            
            String heartbeat_str = "--tomcat run. heartbeat_str";
           Global.heartbeatRecycle(heartbeat_str);
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}