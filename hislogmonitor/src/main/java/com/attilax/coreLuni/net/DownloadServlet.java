package com.attilax.coreLuni.net;
 

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

import sun.misc.BASE64Encoder;
@WebServlet(name="DownloadServlet", urlPatterns={"/DownloadServlet","/dafdafsss22333"},loadOnStartup=1)
public class DownloadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset = utf-8");

		String filename = request.getParameter("username"); //改为自己项目中存在的文件
		File  f=new File(filename);
		//这种方法对部分字符仍存在乱码！
		//String dname = new String("青春纪念册.mp3".getBytes("utf-8"), "iso-8859-1");
		//这种方法最常用！解决文件名乱码
		String dname = filenameEncoding(f.getName(),request);
		
		String contentType = this.getServletContext().getMimeType(filename);
		String contentDisposition = "attachment;filename="+dname;
		response.setHeader("Content-Type", contentType + "");
		response.setHeader("Content-Disposition", contentDisposition);

		FileInputStream input = new FileInputStream(filename);
		ServletOutputStream out = response.getOutputStream();

		//IOUtils.
		IOUtils.copy(input, out);
		input.close();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}


	public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
		String agent = request.getHeader("User-Agent"); //获取浏览器
		if (agent.contains("Firefox")) {
			BASE64Encoder base64Encoder = new BASE64Encoder();
			filename = "=?utf-8?B?"
					+ base64Encoder.encode(filename.getBytes("utf-8"))
					+ "?=";
		} else if(agent.contains("MSIE")) {
			filename = URLEncoder.encode(filename, "utf-8");
		} else {
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}
}
