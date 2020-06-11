package com.attilax.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.attilax.io.pathx;
import com.attilax.web.ProcessInfo;
import com.google.common.collect.Maps;

 

public class Global {
	
	
	public static ThreadLocal<HttpServletRequest> curReq=new ThreadLocal<>();
	public static Map map=Maps.newConcurrentMap();
	
	

    public static Properties getPropFile()   {
		String cfgfile = pathx.prjPath_webrootMode()+ "/cfg.txt";
		InputStream inStream;
		try {
			inStream = new FileInputStream(new File(cfgfile));
		
        Properties prop=new Properties();prop.load(inStream);
		return prop;
		} catch ( Exception e) {
			throw new RuntimeException(e);
		}  
	}
    
    
    public static Properties getPropFile(String f)   {
		String cfgfile = f;
		InputStream inStream;
		try {
			inStream = new FileInputStream(new File(cfgfile));
		
        Properties prop=new Properties();prop.load(inStream);
		return prop;
		} catch ( Exception e) {
			throw new RuntimeException(e);
		}  
	}


	public static String urlencode(String jsonString) {
	try {
		return	URLEncoder.encode(jsonString,"utf8");
	} catch (UnsupportedEncodingException e) {
		throw new RuntimeException(e);
	}
		//return null;
	}


	public static String timestamp() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
		String formatStr =formatter.format(new Date());
		return formatStr;
	}


	public static void heartbeatRecycle(String heartbeat_str) {
		while (true) {
			try {
				int millis_heatbeat_time = 7000;  //human is 70 per minute
				Thread.sleep(millis_heatbeat_time);
				
				System.out.println(heartbeat_str);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("---ok");
		}
	}


	public static void setVarVal(String string, Object value) {
		map.put(string, value);
		
	}


	public static Object getVarVal(String string) {
		// TODO Auto-generated method stub
		return map.get(string);
	}

}
