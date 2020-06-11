package com.attilax.oplog;

import com.alibaba.dubbo.demo.Provider;
import com.attilax.web.EmbeddedTomcat;

public class AAstartAll {

	public static void main(String[] args) {
		
		
		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {
				 Mq2dbTimer.main(null);
				
			}
			
		},"");
		
		
		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {
				EmbeddedTomcat.main(null);
			}
			
		},"");
		
		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {
				Provider.main(null);
			}
			
		},"");
		
		
		
	  
	 System.out.println("--");

	}

}
