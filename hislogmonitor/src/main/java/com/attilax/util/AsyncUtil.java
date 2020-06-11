package com.attilax.util;

import java.util.concurrent.ForkJoinTask;

import org.apache.log4j.Logger;

 

//import com.attilax.exception.ExUtil;

/**
 * v2
 * 
 * @author attilax
 *
 */
public class AsyncUtil {
	public static	Logger lgr = Logger.getLogger(AsyncUtil.class);

	public static void execMeth_Asyn(final Runnable runnable, final String threadName) {
		// attilax 老哇的爪子 0_g_l o8s
		try {
			new AsyncUtil().newThread(runnable, threadName);
		} catch (Exception e) {
			lgr.error(e);
			ExUtil.throwEx(e);
		}

	}

	@Deprecated
	public void execMeth_Ays(final Runnable runnable, final String threadName) {
		// attilax 老哇的爪子 0_g_l o8s
		try {
			newThread(runnable, threadName);
		} catch (Exception e) {
			// core.err(e);
			ExUtil.throwEx(e);
		}

	}

	// public void execMeth_Ays(final Runnable runnable, final String
	// threadName) {
	// // attilax 老哇的爪子 0_g_l o8s
	// try {
	// newThread(runnable,threadName);
	// } catch (Exception e) {
	// // core.err(e);
	// ExUtil.throwEx(e);
	// }
	//
	// }
	//
	public Thread newThread(final Runnable runnable, final String threadName) {

		Thread Thread_ini_fentsiController = new Thread(runnable);
		Thread_ini_fentsiController.setName(threadName);
		Thread_ini_fentsiController.setPriority(Thread.MAX_PRIORITY);
		Thread_ini_fentsiController.start();
		// System.out.println("--thrd:"+threadName);
		return Thread_ini_fentsiController;

	}

	public static Thread execMeth_Asyn(ForkJoinTask<Object> fjtask, String threadName) {
		Thread Thread_ini_fentsiController = new Thread(new Runnable() {
			
			@Override
			public void run() {
				fjtask.fork();
				
			}
		});
		Thread_ini_fentsiController.setName(threadName);
		Thread_ini_fentsiController.setPriority(Thread.MAX_PRIORITY);
		Thread_ini_fentsiController.start();
		// System.out.println("--thrd:"+threadName);
		return Thread_ini_fentsiController;
		
	}
}
