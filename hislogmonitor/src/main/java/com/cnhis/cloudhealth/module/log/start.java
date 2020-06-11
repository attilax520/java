package com.cnhis.cloudhealth.module.log;

import java.io.FileNotFoundException;
import java.rmi.AccessException;
import java.rmi.RemoteException;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.util.Log4jConfigurer;

public class start {

	public static void main(String[] args) throws AccessException, RemoteException, InterruptedException, FileNotFoundException {
		
		
		 Log4jConfigurer.initLogging("classpath:log4j.properties", 5000);
		 PropertyConfigurator.configureAndWatch( "C:\\0wkspc\\hislog\\src\\main\\resources\\log4j.properties"  , 5000);
		server.startServer(80);
		
		
		Cls1.main(null);  //recyel unlimit 
	}

}
