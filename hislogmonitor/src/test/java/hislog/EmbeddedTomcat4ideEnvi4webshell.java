package hislog;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.ClassLoaderFactory;
import org.apache.catalina.startup.Tomcat;
import org.apache.ftpserver.ftplet.FtpException;

import com.attilax.io.pathx;
import com.attilax.net.ftp.ftpapache;
import com.attilax.oplog.AsynUtil;
import com.attilax.util.Global;

import javax.servlet.ServletException;

/**hislog.EmbeddedTomcat4ideEnvi
 * http://localhost:1314/webcon/autoup\autoxV2.htm
 * http://localhost/cloud/clinewadvice/clinicvaf1insert?lver=22
 * http://localhost:1314/webcon/commServletV2?class=com.attilax.io.SpaceCheck&method=spaceinfo&paramtypes=&ar
 * Created by nil on 2014/8/1.
 */
public class EmbeddedTomcat4ideEnvi4webshell {
    private Tomcat tomcat;    
    static int port = 8022;
    static String contextPath = "/webshell";
    static  String baseDir =pathx.webAppPath_jensyegeor();

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
     
    	
//    	AsynUtil.execMeth_Ays(new Runnable() {
//			
//			@Override
//			public void run() {
//				 try {
//					ftpapache.main(null);
//				} catch (FtpException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					
//				}
//				
//			}
//		}, "threadName");
    //	org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher
    //	org.jboss.resteasy.plugins.providers.multipart.MimeMultipartProvider
    	String base=pathx.webAppPath_jensyegeor();
    	System.out.println("");
        try {
           
         
            System.out.println("basedir:"+baseDir);
            		//new File("webroot").getAbsolutePath();  // 项目中web目录名称，以前版本为WebRoot、webapp、webapps，现在为WebContent
            EmbeddedTomcat4ideEnvi4webshell EmbeddedTomcat1 = new EmbeddedTomcat4ideEnvi4webshell();
            EmbeddedTomcat1.startTomcat(port, contextPath, baseDir);
            // 由于Tomcat的start方法为非阻塞方法,加一个线程睡眠模拟线程阻�?
            
            
            String heartbeat_str = "--tomcat run. heartbeat_str";
           Global.heartbeatRecycle(heartbeat_str);
           
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}