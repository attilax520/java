package com.kok.sport.utils;

import java.io.File;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;

import org.apache.catalina.core.AprLifecycleListener;

import org.apache.catalina.core.StandardServer;

import org.apache.catalina.startup.ClassLoaderFactory;

import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

//import com.attilax.io.pathx;
//
//import com.attilax.util.Global;

import javax.servlet.ServletException;

/**
 * 
 * Created by nil on 2014/8/1.
 * 
 */


// http://localhost:13140/methRunner
public class TomcatV6U66 {
	
	public static void main(String[] args) throws LifecycleException {
		System.out.println("args:"+JSON.toJSONString(args));
		int port=Integer.parseInt(args[0].trim()) ;	String ServletPath="/methRunner";
		Tomcat tomcat=new Tomcat();	
		tomcat.setPort(port);
	 	String basedir = "D:\\baidu";
		 tomcat.setBaseDir(basedir);	
		Context Context1_Webapp=	tomcat.addWebapp("/", basedir);//addWebapp()过时了
		String servletName = "servletName";
		tomcat.addServlet(Context1_Webapp, servletName,  new MvcServlet());
		Context1_Webapp.addServletMappingDecoded(ServletPath,servletName);//配置servlet映射路径
		tomcat.start();
		tomcat.getServer().await();
		System.out.println("f");
	}

	public Tomcat tomcat;
	
	
	public void startTomcat(int port, String contextPath, String baseDir,Consumer<Map> consume1)

			throws ServletException, LifecycleException {

		tomcat = new Tomcat();
		tomcat.setPort(port);
		tomcat.setBaseDir(".");

		// StandardServer server = (StandardServer) tomcat.getServer();		
		// AprLifecycleListener listener = new AprLifecycleListener();
		// server.addLifecycleListener(listener);
  //    tomcat.addContext(contextPath, docBase)
// 以为addWebapp()过时了，其实不然，这个方法可以帮我们创建一个默认的web.xml文件，而addContext()没有，
		//这是报404错误的原因，想想也是，貌似servlet 3.0之后可以不用web.xml，用注解来替代，过时应该说这个原因吧。		
		Context Context1=	tomcat.addWebapp(contextPath, baseDir);
	//	tomcat.add
		Map map=Maps.newConcurrentMap();
		map.put("Tomcat", tomcat);
		map.put("Context", Context1);
		consume1.accept(map);
		tomcat.start();
		/**
		 * Tomcat有三种运营模式：bio、nio、apr，不同模式下Tomcat的运行效率差别比较大。 apr（Apache Portable Runtime）
		 * 从操作系统级别来解决异步的IO问题,大幅度的提高性能。
		 * 
		 * 必须要安装apr和native，直接启动就支持apr。
		 */

	}

	public void startTomcat(int port, String contextPath, String baseDir)

			throws ServletException, LifecycleException {

		tomcat = new Tomcat();
		tomcat.setPort(port);
		tomcat.setBaseDir(".");

		// StandardServer server = (StandardServer) tomcat.getServer();		
		// AprLifecycleListener listener = new AprLifecycleListener();
		// server.addLifecycleListener(listener);

		tomcat.addWebapp(contextPath, baseDir);
	//	tomcat.add
		tomcat.start();
		/**
		 * Tomcat有三种运营模式：bio、nio、apr，不同模式下Tomcat的运行效率差别比较大。 apr（Apache Portable Runtime）
		 * 从操作系统级别来解决异步的IO问题,大幅度的提高性能。
		 * 
		 * 必须要安装apr和native，直接启动就支持apr。
		 */

	}

	private void stopTomcat() throws LifecycleException {

		tomcat.stop();

	}
	 
	 
	

}