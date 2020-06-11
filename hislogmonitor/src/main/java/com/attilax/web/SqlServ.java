package com.attilax.web;

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
import com.attilax.db.ResultSetUtil;
import com.attilax.io.pathx;
import com.attilax.shell.CliShellProcessService;
import com.attilax.shell.ProcessUtil;
import com.attilax.util.ExUtil;
import com.attilax.util.csvWzHeadService;

/**
 * v3 add relt path mode 基于本地代理的文件绝对路径选择器 内嵌式ftp server集成测试 文件上传下载与一些移动复制操作方便实施
 * 以及目录浏览
 * 
 * @author attilax
 *
 */
// http://localhost:9999/ShellServlet3?cmdtextbox=/libExe/FileOpenDialog.exe
@WebServlet(name = "SqlSrv", urlPatterns = { "/SqlSrv" }, loadOnStartup = 1)
public class SqlServ extends HttpServlet {
	
	public static void main(String[] args) {
		System.out.println( System.getProperty("os.name"));
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//cmdFrom
		String sql = request.getParameter("cmdtextbox").toString();
		String cmdFrom = request.getParameter("cmdFrom").toString();
		if(cmdFrom.equals("file"))
		{
			String sqlfile=sql;
			if( System.getProperty("os.name").toLowerCase().contains("windows"))
			{
				sqlfile="C:"+sqlfile;
				sqlfile=	pathx.fixSlash2reversSplash(sqlfile);
			}
			System.out.println( sqlfile);
			if(!new File(sqlfile).exists())
				throw new RuntimeException( "not exist file:"+sqlfile);
			sql=FileUtils.readFileToString(new File(sqlfile));
			if(sql.trim().equals(""))
				throw new RuntimeException( "sql is empty");
		}
		
		response.setContentType("text/html;charset = utf-8");

		String cfg = request.getParameter("cfgtextbox"); // 鏀逛负鑷繁椤圭洰涓瓨鍦ㄧ殑鏂囦欢
		Map m = csvWzHeadService.parseDbcfg(cfg);
		// csvWzHeadService.parseDbcfg(s)

		Connection connection = null;
		Statement statement = null;
		String mode = request.getParameter("mode");

		String url = (String) m.get("url");
		String user = (String) m.get("username");
		String password = (String) m.get("password");
		try {
			Class.forName(m.get("driverClass").toString());
		} catch (ClassNotFoundException e1) {
			ExUtil.throwExV2(e1);
		}

		try {
			connection = DriverManager.getConnection(url, user, password);

			Statement preparedStatement = connection.createStatement();// (
																		// request.getParameter("cmdtextbox").toString());
			
			if (mode.equals("query")) {
				ResultSet rs = preparedStatement.executeQuery(sql);
				;
				List li = ResultSetUtil.toList(rs);
				respService.out_asjson(response, li);
			}
			else if (mode.equals("update")) {
				int rs = preparedStatement.executeUpdate(sql);

				respService.out_astoStr(response, rs);
			} else { //
				Object rzt = preparedStatement.execute(sql);
				respService.out_astoStr(response, rzt);
			}

			// System.out.println("是否成功连接pg数据库" + connection);

		} catch (SQLException e) {
			ExUtil.throwExV2(e);
		}

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
