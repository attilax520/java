package com.attilax.coreLuni.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.attilax.util.ExUtil;
import com.attilax.util.timestampUtil;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

//http://localhost/FileServiceServlet?method=read&fpath=C%3a%5c0wkspc%5chislog%5csrc%5cmain%5cresources%5clog4j.properties
///FileServiceServlet?method=read&fpath=C%3a%5c0wkspc%5chislog%5csrc%5cmain%5cresources%5clog4j.properties
// com.attilax.io.FileServiceServlet
//@ // WebServlet(name="FileServiceServlet", urlPatterns={"/FileServiceServlet","/dafdaf"},loadOnStartup=1)
public class FileServiceServlet extends HttpServlet {
	
	
	
	public static void main(String[] args) throws IOException {
		//com.caucho.quercus.servlet.QuercusServlet
	}
	
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      //  String user=request.getParameter("user");
		
		try {
			if( request.getParameter("method").equals("save"))
	    	{
	    		save(request,response);
	    		return;
	    	}
			if( request.getParameter("method").equals("subfiles"))
	    	{
				String fpath=request.getParameter("fpath");
	    		String r=new DirService4monitor().subfiles_retJsonArray(fpath);
	    		 output(r,response);
	    		 return;
	    	}
			
			if( request.getParameter("method").equals("search"))
	    	{
				String fpath=request.getParameter("fpath");
	    		String r=search(fpath,request.getParameter("fname"));
	    		 output(r,response);
	    		 return;
	    	}
			
			
	    	
	    	//------method is read
	    	String fpath=request.getParameter("fpath");
	    	String t=FileUtils.readFileToString(new File(fpath),request.getParameter("encode"));
	    	
	         
	    	  output(t,response);
		} catch (Throwable e) {
			 String e_json=JSON.toJSONString(e);
			   output(e_json,response);
		}
    
      
       
    }
    private String search(String fpath, final String fname) {
    	final List<Map> fils=Lists.newArrayList();
		DirTraveService dts=new DirTraveService();
		final String dir = fpath;
		dts.traveV4Vs427(dir, new Function<File, Object>() {

			@Override
			public Object apply(File arg0) {
				logger.info("arg0:"+arg0.getAbsolutePath());
			 
				try {
					if(arg0.getAbsolutePath().contains(fname))
					{
						Map m=Maps.newConcurrentMap();
						m.put("f", arg0.getAbsolutePath());
						fils.add(m);
					}
				} catch (Exception e) {
					 
					e.printStackTrace();
					logger.error(e);
				}
				return null;
			}
		},  DirTraveService.nullFunc()    );
		String r=JSON.toJSONString(fils,true);
		return r;
	}


	private void save(HttpServletRequest request, HttpServletResponse response) {
    	String fpath=request.getParameter("fpath");
    	String txt=request.getParameter("txt");
    	if(txt==null )
    	{
    		throw new RuntimeException("txt is null");
     	
    	}
    	try {
			FileUtils.writeStringToFile(new  File(fpath), txt);
		} catch (IOException e) {
			 ExUtil.throwExV2(e);
			  
		}
    	
    	 
	}
    public static Logger logger = Logger.getLogger(FileServiceServlet.class);

	private void output(String t, HttpServletResponse response) {
		response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer;
		try {
			writer = response.getWriter();
		//   String s=FileUtils.readFileToString(new File("c:\\d\\http%3A%2F%2Flocalhost%3A8080%2Fclidoctorweb%2Fcloud%2Fclinewadvice%2FzhenLiaoPinCiList.txt"));
	           writer.append(t);
		} catch (IOException e) {
			logger.error(e);
		}
        
        
        
	}


	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
 
}
