package com.attilax.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class futuretaskTest {

	
	public static ExecutorService pool_ExecutorService  = Executors.newFixedThreadPool(200);
	{
		{
		
	//	preaddThredpool();
		}
	}
	
	public static String preadd=preaddThredpool() ;
	private static String preaddThredpool() {
		for(int i=0;i<200;i++)
		{
			System.out.println("stat ini");
		}
		return "ok";
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		
	//	preaddThredpool();
		 FutureTask<Object> FutureTask_updateCviBdj=new FutureTask( new Callable<Object>() {

				@Override
				public Object call()   {				 
				 
					return "rzt";
				}
			}) ;
		 pool_ExecutorService.submit(FutureTask_updateCviBdj, "threadName");
		 System.out.println(FutureTask_updateCviBdj.get());
	}

}
