package com.attilax.core;
//package com.cnhis.cloudhealth.hosdoctorweb.util;

//import com.cnhis.cloudhealth.commons.aoplog.util.Logger;
//import com.cnhis.cloudhealth.commons.aoplog.util.smpLogger;

public class AsynUtil {
//	private static Logger logger=new smpLogger();
	public static void execMeth_Ays(Runnable runnable, String threadName) {
		try {
			newThread(runnable,threadName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static Thread newThread(Runnable runnable, String threadName) {
		Thread Thread_ini_fentsiController = new Thread(runnable);
		Thread_ini_fentsiController.setName(threadName);
		Thread_ini_fentsiController.setPriority(Thread.MAX_PRIORITY);
		Thread_ini_fentsiController.start();
		// System.out.println("--thrd:"+threadName);
		return Thread_ini_fentsiController;
		
	}

	public static void execMeth_AysIniblock(Runnable runnable, String threadName) {
		try {
			newThread(runnable,threadName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
