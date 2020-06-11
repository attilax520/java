package com.cnhis.cloudhealth.module.log;

import java.util.Timer;

import org.apache.log4j.Logger;

 

public class Cls1 {
	public static Logger logger = Logger.getLogger(Cls1.class);
	public static void main(String[] args) throws InterruptedException {
		
		//Timer timer=
		
		while(true)
		{
			logger = Logger.getLogger(Cls1.class);
			Thread.sleep(2000);
			logger.info("--info");logger.debug("--debug");
		}
		
	}

}
