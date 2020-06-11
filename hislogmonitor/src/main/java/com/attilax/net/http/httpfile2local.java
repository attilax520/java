package com.attilax.net.http;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;

import com.attilax.util.ExUtil;


//   com.attilax.net.http.httpfile2local   http://192.168.1.18:1314/webdavapp/webdavurl/home/cnhis/cloudhealth/logs/log.log  c:/tmp/cloudhealthtmp.log
public class httpfile2local {

	public static void main(String[] args) throws IOException {
		final String http=args[0].trim();  //http://192.168.1.18:1314/webdavapp/webdavurl/home/cnhis/cloudhealth/logs/log.log  c:/tmp/cloudhealthtmp.log
		final String local=args[1].trim();     // c:/tmp/cloudhealthtmp.log
	//	List<String> list = ["item"]; // 向List集合里面添加元素
	//	Set<String> set = {"item"}; // 向Set集合里面添加元素
		Timer tmr=new Timer();
		tmr.schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					http2loc(http, local);
					System.out.println( http+local );
				} catch (Exception e) {
					 
					e.printStackTrace();
				}
				
			}
		}, 100, 5000);
	
				

	}

	public static void http2loc(String http, String local)   {
		byte[] rzt=  HttpClientUtil.newx().httpget_retBytearr(http);
		try {
			FileUtils.writeByteArrayToFile(new File(local), rzt);
		} catch (IOException e) {
			ExUtil.throwExV2(e);
		}
	}

}
