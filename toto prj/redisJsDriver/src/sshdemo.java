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
import com.attilax.io.IOUtilsStreamUtil;
import com.attilax.util.PrettyUtilV2t33;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class sshdemo {

	public static void main(String[] args) throws Exception {
		Connection con = new Connection("101.132.148.11", 22);

		con.connect();

		boolean isAuthed;

		isAuthed = con.authenticateWithPassword("root", "pdm#1921");

		Session session = con.openSession();
		String redisCmd = "redis-cli -h 101.132.148.11 -p 63790 -a tt  ";
		System.out.println(redisCmd);
		session.execCommand(redisCmd);

		List<String> result_tmp = new ArrayList<>();



		OutputStream oStream = session.getStdin();
		String auth = "auth ttredis$2018*124";

		IOUtilsStreamUtil.	execV2(oStream, auth);
		result_tmp =IOUtilsStreamUtil. readLines(session.getStdout());
		System.out.println(result_tmp);
	   

		// String get ="get access_token";// + "\n\n ";

		System.out.println("----------------------\r\n");
		result_tmp.clear();
	IOUtilsStreamUtil.	execV2(oStream,"get access_token");
		Thread.sleep(500);
		result_tmp =IOUtilsStreamUtil. readLines(session.getStdout());
	
		System.out.println(result_tmp);
	 

		// get access_token
		System.out.println("----------------------\r\n");
		result_tmp.clear();
		IOUtilsStreamUtil.	execV2(oStream, "smembers 300348232050020352_2019_04_02");
		result_tmp =IOUtilsStreamUtil. readLines(session.getStdout());
		
	System.out.println(PrettyUtilV2t33.showListObjV2(result_tmp));	  ;

		System.out.println("--f");
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
