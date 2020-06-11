package com.attilax.oplog;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.attilax.io.pathx;
import com.attilax.mq.PTPReceive;
import com.attilax.mq.TOPReceiv;
import com.attilax.util.Callback;
import com.attilax.web.requestImp;

import cn.freeteam.util.MybatisSessionFactory;

public class Mq2dbTimer {

	public static void main(String[] args) {

		new PTPReceive().start(new Callback() {

			@Override
			public void process(Object text) {
				try {
					JSONObject jo=JSONObject.parseObject((String) text);
					 new OperLogUtil().log4postgre_core(jo.getString("loginname"),jo.getString("content") ,"localMq" );

				} catch (Exception e) {
					System.out.println( "---Mq2dbTimer  PTPReceive  Callback  process err" );
					e.printStackTrace();
				}
				 
			}
		});
		
		AsynUtil.execMeth_Ays(new Runnable() {
			
			@Override
			public void run() {
				TOPReceiv topReceiv = new TOPReceiv();
				topReceiv. cfgfile = pathx.prjPath_webrootMode()+ "/cfg.txt";
				topReceiv.start(new Callback() {

					@Override
					public void process(Object text) {
						try {
							 JSONObject jo=JSONObject.parseObject((String) text);
							 new OperLogUtil().log4postgre_core(jo.getString("loginname"),(String) text ,"remoteMq" );
						} catch (Exception e) {
							System.out.println( "---Mq2dbTimer  topReceiv  Callback  process err" );
							e.printStackTrace();
						}
						

					}
				});
				
			}
		}, "Mq2dbTimer threadName");
		System.out.println("--f");
		
		
	}

}
