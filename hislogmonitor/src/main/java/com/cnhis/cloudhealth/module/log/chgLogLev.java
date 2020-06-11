package com.cnhis.cloudhealth.module.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.rmi.Remote;

import org.apache.log4j.Appender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.util.Log4jConfigurer;

public class chgLogLev  implements Remote ,Serializable{
	
	public static Logger logger = Logger.getLogger(Cls1.class);
	public static void main(String[] args) {
		System.out.println(org.apache.log4j.Level.DEBUG);
		//org.apache.log4j.app
		//org4j.appender.CONSOLE.Threshold=DEBUG
	}
	public void chglev(org.apache.log4j.Level lev) throws FileNotFoundException
	{
		Logger.getRootLogger().setLevel(lev);
	
		
		Appender appender = Logger.getRootLogger().getAppender("stdout");
		((org.apache.log4j.ConsoleAppender)appender).setThreshold(Priority.DEBUG);
		
		 LogManager.getRootLogger().setLevel(lev);  
		 
		 Log4jConfigurer.initLogging("classpath:log4j.properties", 5000);
		 PropertyConfigurator.configureAndWatch( "C:\\0wkspc\\hislog\\src\\main\\resources\\log4j.properties"  , 5000);
		logger.info("chgLogLev¡£chglev£¨£© to  "+lev);
		
	}

}
