package com.attilax.prj.hislog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.attilax.device.perfMonitorService;
import com.attilax.io.pathx;
import com.attilax.shell.CliShellProcessService;
import com.attilax.shell.ProcessUtil;
import com.attilax.web.respService;

/**
 * v3 add relt path mode 基于本地代理的文件绝对路径选择器 内嵌式ftp server集成测试 文件上传下载与一些移动复制操作方便实施
 * 以及目录浏览
 * 
 * @author attilax
 *
 */
// http://localhost:9999/ShellServlet3?cmdtextbox=/libExe/FileOpenDialog.exe
@WebServlet(name = "commServlet", urlPatterns = { "/commServlet", "/commServletprjhislog" }, loadOnStartup = 1)
public class commServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset = utf-8");

		String method = request.getParameter("method"); // 鏀逛负鑷繁椤圭洰涓瓨鍦ㄧ殑鏂囦欢
		Map m = JSON.parseObject(request.getParameter("params"));
		if (method.equals("cpuservice.sysinfo")) {   //
			Object o = new perfMonitorService().sysinfo(m);
			respService.out_asjson(response, o);
		}

	}

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
