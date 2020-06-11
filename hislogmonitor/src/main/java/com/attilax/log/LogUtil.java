package com.attilax.log;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	public static  Logger lgr = Logger.getLogger(LogUtil.class);
	public static void main(String[] args) {System.out.println("11");
		LogUtil.redirectSystemOutAndErrToLog_slf4j();
		System.out.println("22");
		org.slf4j.Logger l;
	}
	
	   
    public static void redirectSystemOutAndErrToLog_slf4j() {  
    	Logger logger = LoggerFactory.getLogger("LogUtil");  
    //	 Logger logger = LoggerFactory.getLogger("Com_jacob_office_Util");
        PrintStream printStreamForOut = StdOutErrRedirector4slf4j.createLoggingWrapper(System.out, false,logger);  
        PrintStream printStreamForErr = StdOutErrRedirector4slf4j.createLoggingWrapper(System.out, true,logger);  
        System.setOut(printStreamForOut);  
        System.setErr(printStreamForErr);  
    } 
    
    
	public static long infoTime(Logger lgr,String op,long startTimeMills) {
		   long l2 = System.currentTimeMillis();
	        long b =(l2-startTimeMills);
	        String logstr = "----------------------------------------------@op@，耗时："+b+"豪秒------------------------------------------------------";
	        logstr=logstr.replaceAll("@op@", op);
	        lgr.info(logstr);
	        
	        long l23 = System.currentTimeMillis();
	        return l23;
		
	}

	
	public static void log_sf(String f, Map map) {
		try {
			log(f,map);
		} catch (Exception e) {
			lgr.error(e);
		}
	
	}
	public static void log(String f, Map map) {
		String json=JSON.toJSONString(map);
		try {
			FileUtils.write(new File(f), json);
		} catch (IOException e) {
			ExUtil.throwExV2(e);
		}
		
	}

}
