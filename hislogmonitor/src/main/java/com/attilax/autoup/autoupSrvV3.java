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
import com.attilax.util.cli.SSHHelper;
import com.attilax.web.respService;

/**
 * v3 add relt path mode 基于本地代理的文件绝对路径选择器 内嵌式ftp server集成测试 文件上传下载与一些移动复制操作方便实施
 * 以及目录浏览
 * 
 * @author attilax
 *
 */
// http://localhost:9999/ShellServlet3?cmdtextbox=/libExe/FileOpenDialog.exe
@WebServlet(name = "autoupSrvV3", urlPatterns = { "/autoupSrvV3" }, loadOnStartup = 1)
public class autoupSrvV3 extends HttpServlet {

	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		String zipFilePath = request.getParameter("cmdtextbox").toString();
		String cmdFrom = request.getParameter("cmdFrom").toString();
		String charset_console_ret = request.getParameter("encode").toString();

		String jarupzippath = request.getParameter("jarupzippath").toString();

		String rztContainId = request.getParameter("rztContainId").toString();
		response.setContentType("text/html;charset = utf-8");
		autoupdate autoupdate = new autoupdate();
		
		String svrinfo= request.getParameter("svrinfo");
		if(svrinfo==null || svrinfo.trim().length()==0)
		{
		// cmdFrom
			
	
		
			try {
	
				zipFilePath = pathx.toOsPath(zipFilePath);
	
				Object ret = autoupdate.exeUpdate(zipFilePath, charset_console_ret, jarupzippath, rztContainId);
				respService.out_asjson(response, ret);
			} catch (Exception e) {
				respService.out_asjson(response, e);
			}
		}else
		{  //rmt linux mode
			String[] a=svrinfo.trim().split(",");
			SSHHelper SSHHelper1 = null;
			 
				try {
					SSHHelper1 = new SSHHelper(a[0], 22, a[1], a[2]);
				} catch (Exception e1) {
					ExUtil.throwExV2(e1);
				}
			 
			autoupdate.SSHHelper1=SSHHelper1;
			Object ret;
			try {
				ret = autoupdate.exeUpdate_rmt_Linux_V2( jarupzippath, charset_console_ret,  rztContainId);
				respService.out_asjson(response, ret);
			} catch (Exception e) {
				respService.out_asjson(response, e);
			}
			 
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
