package redisJsDriverPkg;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.apache.catalina.LifecycleException;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.attilax.mvc.MvcUtil;
import com.attilax.net.reqUtilV2t33;

//redisJsDriverPkg.redisJsDriver
public class redisJsDriver {

	
	//  port  basedir
	public static void main(String[] args) throws ServletException, LifecycleException {
		// javax.servlet.ServletContext
		// javax.servlet.ServletContext.getVirtualServerName
		// 获得当前项目路径get cur prj path
		// new File("webroot").getAbsolutePath(); //
		// 项目中web目录名称，以前版本为WebRoot、webapp、webapps，现在为WebContent
		// System.out.println(new File("").getAbsolutePath());
		System.out.println(JSON.toJSON(args));
		int port = Integer.parseInt(args[0].trim());

		String contextPath = "/";

		String baseDir = new File("").getAbsolutePath();// pathx.webAppPath_jensyegeor();

		System.out.println("app basedir:" + baseDir);
		baseDir=args[1].trim();
		
		System.out.println("app basedir from args:" + baseDir);
		TomcatV3T33 tomcatV3T33aTomcatV3T33 = new TomcatV3T33();

		tomcatV3T33aTomcatV3T33.startTomcat(port, contextPath, baseDir);

		tomcatV3T33aTomcatV3T33.tomcat.getServer().await();

		String heartbeat_str = "--tomcat run. heartbeat_str";

		// Global.heartbeatRecycle(heartbeat_str);

	}
	
	 	
	@Path("/api_redis")
	public Object get(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String cmdString=req.getParameter("param");	
	
		String outEncode=reqUtilV2t33.getParameter("seriali",req,"utf8");
	
		Object v=RedisUtil.parse(cmdString);
 		if((reqUtilV2t33.getParameter("seriali",req)=="") || reqUtilV2t33.getParameter("seriali",req)=="auto")
		{
			if( v instanceof String )
				MvcUtil.outputHtml(resp, v.toString());
			else {
				MvcUtil.outputHtml(resp, JSON.toJSONString(v, true));
			}
		}
		else if(reqUtilV2t33.getParameter("seriali",req)=="json")
			MvcUtil.outputHtml(resp, JSON.toJSONString(v, true));
		else if(reqUtilV2t33.getParameter("seriali",req)=="str")
			MvcUtil.outputHtml(resp,v.toString());
		 
	    return v;
	}


}
