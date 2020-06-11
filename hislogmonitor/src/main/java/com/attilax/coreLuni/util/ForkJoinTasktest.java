package com.attilax.coreLuni.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.tomcat.websocket.AsyncChannelGroupUtil;

public class ForkJoinTasktest {

	public static void main(String[] args) {
		System.out.println("--ForkJoinTasktest ");
		ForkJoinTask<Object> fjtask = new RecursiveTask<Object>() {

			@Override
			protected Object compute() {
				System.out.println("--task1");
				
				return null;
			}
		};
	//	fjtask.fork(); // exec
		AsyncUtil.execMeth_Asyn(fjtask, "threadName");
//		ForkJoinTask<Object> fjtask2 = new RecursiveTask<Object>() {
//
//			@Override
//			protected Object compute() {
//				System.out.println("--task2");
//				return "task2rzt";
//			}
//		};
//		fjtask2.fork();
		// fjtask2.compareAndSetForkJoinTaskTag(e, tag)

	//	fjtask.join(); // must fork beir jwju le
	//	System.out.println(fjtask2.join());
		System.out.println("--main ");
	}

}
