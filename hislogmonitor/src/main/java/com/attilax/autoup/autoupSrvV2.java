package com.attilax.autoup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.attilax.collection.mapBuilder;
import com.attilax.db.ResultSetUtil;
import com.attilax.io.pathx;
import com.attilax.shell.CliShellProcessService;
import com.attilax.shell.ProcessUtil;
import com.attilax.util.ExUtil;
import com.attilax.util.csvWzHeadService;
import com.attilax.web.respService;

/**
 * v3 add relt path mode 基于本地代理的文件绝对路径选择器 内嵌式ftp server集成测试 文件上传下载与一些移动复制操作方便实施
 * 以及目录浏览
 * 
 * @author attilax
 *
 */
// http://localhost:9999/ShellServlet3?cmdtextbox=/libExe/FileOpenDialog.exe
@WebServlet(name = "autoupSrvV2", urlPatterns = { "/autoupSrvV2" }, loadOnStartup = 1)
public class autoupSrvV2 extends HttpServlet {
	
	public static void main(String[] args) {
		System.out.println( System.getProperty("os.name"));
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//cmdFrom
		String zipFilePath = request.getParameter("cmdtextbox").toString();
		String cmdFrom = request.getParameter("cmdFrom").toString();
		String charset_console_ret = request.getParameter("encode").toString();
		
		String jarupzippath = request.getParameter("jarupzippath").toString();
		String rztContainId= request.getParameter("rztContainId").toString();
		
		response.setContentType("text/html;charset = utf-8");
		autoupdate autoupdate = new autoupdate();
		try {
		
			
			zipFilePath=pathx.toOsPath(zipFilePath);
			
			Object ret = autoupdate.exeUpdate(zipFilePath,charset_console_ret,jarupzippath,rztContainId);
			respService.out_asjson(response, ret);
		} catch (Exception e) {		   
			respService.out_asjson(response, e);
		}
		
//		String cfg = request.getParameter("cfgtextbox"); // 鏀逛负鑷繁椤圭洰涓瓨鍦ㄧ殑鏂囦欢
//		Map m = csvWzHeadService.parseDbcfg(cfg);
		// csvWzHeadService.parseDbcfg(s)
 
	 

	}

	// private void respService_out(HttpServletResponse response, List li)
	// throws IOException {
	// respService.out(response,JSON.toJSONString(li).getBytes());
	//
	// }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

	// public static String filenameEncoding(String filename, HttpServletRequest
	// request) throws IOException {
	// String agent = request.getHeader("User-Agent"); //鑾峰彇娴忚鍣�
	// if (agent.contains("Firefox")) {
	// BASE64Encoder base64Encoder = new BASE64Encoder();
	// filename = "=?utf-8?B?"
	// + base64Encoder.encode(filename.getBytes("utf-8"))
	// + "?=";
	// } else if(agent.contains("MSIE")) {
	// filename = URLEncoder.encode(filename, "utf-8");
	// } else {
	// filename = URLEncoder.encode(filename, "utf-8");
	// }
	// return filename;
	// }
}
