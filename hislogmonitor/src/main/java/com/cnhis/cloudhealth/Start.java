package com.cnhis.cloudhealth;

import java.io.FileNotFoundException;
import java.rmi.AccessException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
//import org.springframework.util.Log4jConfigurer;

import com.attilax.oplog.AsynUtil;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.cnhis.cloudhealth.module.log.server;

public class Start {
	public static Logger logger = Logger.getLogger(Start.class);
	public static void main(String[] args) throws FileNotFoundException, AccessException, RemoteException, InterruptedException {
	//	、、 Log4jConfigurer.initLogging("classpath:log4j.properties", 5000);
		
		
		
		 PropertyConfigurator.configureAndWatch( "C:\\0wkspc\\hislog\\src\\main\\resources\\log4j.properties"  , 5000);
	System.out.println("dddd--");
		 AsynUtil.execMeth_Ays(new Runnable() {
			
			@Override
			public void run() {
				try {
					Cls1.main(null);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error(e);
				}  //recyel unlimit 
				
			}
		}, "threadName");
		
		 
	//	 server.startServer(80);
		 EmbeddedTomcat4ideEnvi.main(null);
		
		
	
	}

}
