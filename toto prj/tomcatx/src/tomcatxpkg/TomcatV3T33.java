package tomcatxpkg;

import java.io.File;

import org.apache.catalina.LifecycleException;

import org.apache.catalina.core.AprLifecycleListener;

import org.apache.catalina.core.StandardServer;

import org.apache.catalina.startup.ClassLoaderFactory;

import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

//import com.attilax.io.pathx;
//
//import com.attilax.util.Global;

import javax.servlet.ServletException;

/**
 * 
 * Created by nil on 2014/8/1.
 * 
 */

public class TomcatV3T33 {

	private Tomcat tomcat;

	private void startTomcat(int port, String contextPath, String baseDir)

			throws ServletException, LifecycleException {

		tomcat = new Tomcat();
		tomcat.setPort(port);
		tomcat.setBaseDir(".");

		// StandardServer server = (StandardServer) tomcat.getServer();		
		// AprLifecycleListener listener = new AprLifecycleListener();
		// server.addLifecycleListener(listener);

		tomcat.addWebapp(contextPath, baseDir);

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
	@Test
	public void frt()
	{
		String baseDir="C:\\Users\\zhoufeiyue\\Desktop\\dist(11)\\dist";
		TomcatV3T33 tomcatV3T33aTomcatV3T33 = new TomcatV3T33();

		try {
			tomcatV3T33aTomcatV3T33.startTomcat(80, "/", baseDir);
			tomcatV3T33aTomcatV3T33.tomcat.getServer().await();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

	}

	public static void main(String[] args) throws ServletException, LifecycleException {

		// 获得当前项目路径get cur prj path
		// new File("webroot").getAbsolutePath(); //
		// 项目中web目录名称，以前版本为WebRoot、webapp、webapps，现在为WebContent
		System.out.println(new File("").getAbsolutePath());

		int port = 80;

		String contextPath = "/";

		String baseDir = new File("").getAbsolutePath();// pathx.webAppPath_jensyegeor();

		System.out.println("app basedir:" + baseDir);
		TomcatV3T33 tomcatV3T33aTomcatV3T33 = new TomcatV3T33();

		tomcatV3T33aTomcatV3T33.startTomcat(port, contextPath, baseDir);

		tomcatV3T33aTomcatV3T33.tomcat.getServer().await();

		String heartbeat_str = "--tomcat run. heartbeat_str";

		// Global.heartbeatRecycle(heartbeat_str);

	}

}