package com.kok.sport.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.alibaba.fastjson.util.IOUtils;
//import com.attilax.io.IOUtilsStreamUtil;
//import com.attilax.util.PrettyUtilV2t33;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class sshdemo {

	public static void main(String[] args) throws Exception {
		Connection con = new Connection("202.46.38.72", 59157);

		con.connect();

		boolean isAuthed;

		isAuthed = con.authenticateWithPassword("tomati", "tomati123123");
		if(!isAuthed)
			throw new RuntimeException("con.authenticateWithPassword fail");

		Session session = con.openSession();
		//String redisCmd = "redis-cli -h 101.132.148.11 -p 63790 -a tt  ";
		String redisCmd = "top -bn 1 ";
		System.out.println(redisCmd);
		session.execCommand(redisCmd);

		//List<String> result_tmp = new ArrayList<>();



		 

		 
		InputStream stdout = session.getStdout();
		List<String>	result_tmp =org.apache.commons.io.IOUtils.readLines(stdout);
		System.out.println(result_tmp);
		
	 	int CpuuseRate=parseCpuUse(result_tmp);
		int MemuseRate=memMonitorLinux.parseMemusePercent(result_tmp);

		System.out.println("CpuuseRate :"+CpuuseRate);
	  System.out.println( "mem use rate:"+MemuseRate);

		System.out.println("----------------------\r\n");
		result_tmp.clear();
	 
		System.out.println("--f");
	}



	private static int parseCpuUse(List<String> result_tmp) {
		for (String line : result_tmp) {
			line = line.trim();
			if (line.trim().toLowerCase().startsWith("%cpu")) {
				int stat = line.indexOf(":");
				int end = line.indexOf("us");
				String use = line.substring(stat + 1, end);
				use = use.trim();
				Double parseDouble = Double.parseDouble(  use);
				return  parseDouble.intValue();
			}
		}
		throw new RuntimeException(" not imp");
	}

	
//	new Thread(new Runnable() {
//
//		@Override
//		public void run() {
//			try {
//				out2li(result, session);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//	}).start();
//
//	new Thread(new Runnable() {
//
//		@Override
//		public void run() {
//			try {
//				InputStream is = new StreamGobbler(session.getStderr());// 获得标准输出流
//				BufferedReader brs = new BufferedReader(new InputStreamReader(is));
//				for (String line = brs.readLine(); line != null; line = brs.readLine()) {
//					System.out.println("errStream:>" + line);
//				}
//
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//	}).start();
	// logger.info(" coll err out ok");


}
