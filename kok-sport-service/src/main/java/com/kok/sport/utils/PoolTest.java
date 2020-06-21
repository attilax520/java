package com.kok.sport.utils;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.kok.sport.utils.db.MysqlInsertUtil;

public class PoolTest {
	public static ExecutorService pool_esvr = Executors.newFixedThreadPool(10); 
	public static void main(String[] args) {
		  Future  Future1=	pool_esvr.submit(new Runnable() {
				
				@Override
				public void run() {
					 System.out.println(new Date());
				}
			});
		  System.out.println("f");
		  
		  
		

	}

}
