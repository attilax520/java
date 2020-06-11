package com.attilax.oplog;

import com.attilax.log.Logger;
import com.attilax.log.smpLogger;

public class AsynUtil {
	private static Logger logger=new smpLogger();
	public static void execMeth_Ays(Runnable runnable, String threadName) {
		try {
			newThread(runnable,threadName);
		} catch (Exception e) {
			logger.err(e);
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

}
