package com.attilax.coreLuni.net;
 

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.attilax.io.pathx;
@WebServlet(name="UploadServlet15", urlPatterns={"/UploadServlet15" },loadOnStartup=1)
public class UploadServlet1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		//创建工厂
		DiskFileItemFactory factory = new DiskFileItemFactory() ;
		//通过工厂创建解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			List<FileItem> list = upload.parseRequest(request);
			FileItem item1 = list.get(0);
			FileItem item2 = list.get(1);
			
			String path = item1.getString("utf-8");
			System.out.println("普通表单项："+item1.getName()+"="+path);
			System.out.println("文件表单项:");
			System.out.println("Content-Type"+item2.getContentType());
			System.out.println("filename="+item2.getName());
			System.out.println("size = "+item2.getSize());
			String pathname = path+ File.separator+ item2.getName();
			FileUtils.forceMkdir(new File(path ));
			item2.write(new File(pathname ));
			
			if(! pathname.trim().toLowerCase().contains(":") )   //rlt path
			if( pathx.isWindows())
			{ 
				
				pathname=pathx.toOsPath(pathname);
				
			}
			 
			respService.out_astoStr(response, pathname);
			
			
		} catch (Exception e) {
			throw new RuntimeException() ;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
