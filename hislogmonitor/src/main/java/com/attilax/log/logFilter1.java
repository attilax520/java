package com.attilax.log;

import java.io.ByteArrayOutputStream;
import java.io.File;
//package me.gacl.web.filter;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
//import org.apache.zookeeper.common.PathUtils;

import com.alibaba.fastjson.JSON;
import com.attilax.str.Strutil;
import com.attilax.util.PathUtil;
import com.attilax.util.timestampUtil;
//import com.cnhis.cloudhealth.clidoctorweb.gzip.MyHttpServletResponse;
//import com.cnhis.cloudhealth.clidoctorweb.gzip.PathUtil;
//import com.cnhis.cloudhealth.clidoctorweb.gzip.Strutil;
import com.attilax.web.MyHttpServletResponse;

 
 

/**
 * 使用注解标注过滤器
 * 
 * @WebFilter将一个实现了javax.servlet.Filte接口的类定义为过滤器 属性filterName声明过滤器的名称,可选
 *                                               属性urlPatterns指定要过滤
 *                                               的URL模式,也可使用属性value来声明.(
 *                                               指定要过滤的URL模式是必选属性)
 */
@WebFilter(filterName = "zipFilter1", urlPatterns = "/*")
public class logFilter1 implements Filter {

	@Override
	public void destroy() {
		System.out.println("过滤器销毁");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response1, FilterChain chain)
			throws IOException, ServletException {

		// System.out.println("---request.getServletContext():" +
		// request.getServletContext().);
		System.out.println("---执行过滤操作");

		HttpServletResponse response = (HttpServletResponse) response1;
		HttpServletRequest request_http = (HttpServletRequest) request;
		String requestURL = request_http.getRequestURL().toString();
		System.out.println("---request.request).getRequestURL():" + requestURL);
		if (requestURL.contains("laypage.js"))
			System.out.println("dbg");

		// 创建HttpServletResponse 包装类的实例
		MyHttpServletResponse myResponse = new MyHttpServletResponse((HttpServletResponse) response);

		chain.doFilter(request, myResponse);

		

		// myResponse.get

		//lejye repose
		byte[] buff = myResponse.getBufferedBytes();
		
		
		       //----------------if jpg gif png not zip
		       String extedExtname=".jpg,.gif,.png,.gif,.html,.htm,.js,.jsp";
		       if( Strutil.containsAny_sf(requestURL.toLowerCase(), Strutil.toSet(extedExtname, ",")))
				  {
					  outputOri(response, buff);
					return;
				}
				
				
				
				
				//---------step2  if not compressableMimeType ,out ori if not txt ,no zip
				String compressableMimeType = "text/html,text/xml,text/javascript,text/css,text/plain,application/json,application/javascript"; 
				String ContentType = response.getContentType();
				
			 
				
				// String ContentType2 = myResponse.getHeader("Content-Type");
				if (ContentType != null && !Strutil.containsAny_sf(ContentType, Strutil.toSet(compressableMimeType, ","))) {
					outputOri(response, buff);
					return;
				}
				if(ContentType==null)  //ori
				{
					outputOri(response, buff);
					return;
				}
				
	 
	 
		String filenameEncode = PathUtil.toFilenameEncode(requestURL);
		String timestamp=timestampUtil.gettimeStamp_millsec_filenameEncode();
		String queryStr=request_http.getQueryString();
		String getParameterMap=JSON.toJSONString(request_http.getParameterMap()) ;
		
		
		saveLoginfo("c:\\0log_url_param",timestamp+filenameEncode+".txt",("requestURL:"+requestURL+"\r\n").getBytes(),true);
		saveLoginfo("c:\\0log_url_param",timestamp+filenameEncode+".txt",("getParameterMap:"+getParameterMap+"\r\n").getBytes(),true);
		saveLoginfo("c:\\0log_url_param",timestamp+filenameEncode+".txt",("queryStr:"+queryStr+"\r\nretdata:").getBytes(),true);
		saveLoginfo("c:\\0log_url_param",timestamp+filenameEncode+".txt",  buff,true);
	 
		outputOri(response, buff);

	}

	private void saveLoginfo(String dir, String f, byte[] buff ,boolean append ) {
	
		 try {
				FileUtils.forceMkdir(new File(dir));
			FileUtils.writeByteArrayToFile(new File( dir+File.separator+f), buff, append); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private boolean isAjaxData(String requestURL, String string) {
		// TODO Auto-generated method stub
		return false;
	}

	private void outputOri(HttpServletResponse response, byte[] buff) throws IOException {
		response.setContentLength(buff.length);
		  response.getOutputStream().write(buff);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("--过滤器初始化");
	}
}