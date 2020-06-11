package com.attilax.web;
 

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.attilax.io.pathx;
import com.attilax.shell.CliShellProcessService;
import com.attilax.shell.ProcessUtil;
 
/**
 * v3   add relt path mode
 * 基于本地代理的文件绝对路径选择器
 * 内嵌式ftp server集成测试   文件上传下载与一些移动复制操作方便实施  以及目录浏览
 * 
 * @author attilax
 *
 */
 //   http://localhost:9999/ShellServlet3?cmdtextbox=/libExe/FileOpenDialog.exe
@WebServlet(name="ShellServlet3", urlPatterns={"/ShellServlet3","/dafdafsss2233344v3"},loadOnStartup=1)
public class ShellServlet3 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset = utf-8");

		String cmd = request.getParameter("cmdtextbox"); //鏀逛负鑷繁椤圭洰涓瓨鍦ㄧ殑鏂囦欢
		String cwd = request.getParameter("cwd");//curr workdir
		String charsetName = "utf8";charsetName = "gbk";
		//Process Process1 = Runtime.getRuntime().exec(cmd);	
		if(cmd.startsWith("/"))  //rlt path
		{
			String base=pathx.prjPath_webrootMode();
			cmd=base+cmd;
		}
			
 	
	 
		Process 	Process1=   CliShellProcessService.start(cmd,cwd);  
	//	Thread.sleep(1000);
		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
		System.out.println(stdout_2str_ByIoutil);
		String erroutsplotor = "---------errout::is\r\n";
		System.out.println(erroutsplotor);
		 String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
		System.out.println(erroutput2strByIoutil);
		
		
		 
		 String finalstr=stdout_2str_ByIoutil+ erroutsplotor+erroutput2strByIoutil;
		 
		ServletOutputStream out = response.getOutputStream();
		out.write( stdout_2str_ByIoutil.getBytes() );
		 
	 

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}


//	public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
//		String agent = request.getHeader("User-Agent"); //鑾峰彇娴忚鍣�
//		if (agent.contains("Firefox")) {
//			BASE64Encoder base64Encoder = new BASE64Encoder();
//			filename = "=?utf-8?B?"
//					+ base64Encoder.encode(filename.getBytes("utf-8"))
//					+ "?=";
//		} else if(agent.contains("MSIE")) {
//			filename = URLEncoder.encode(filename, "utf-8");
//		} else {
//			filename = URLEncoder.encode(filename, "utf-8");
//		}
//		return filename;
//	}
}
