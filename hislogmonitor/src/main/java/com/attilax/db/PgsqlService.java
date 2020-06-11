package com.attilax.db;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.postgresql.jdbc.TimestampUtils;

import com.attilax.oplog.AsynUtil;
import com.attilax.util.timestampUtil;

public class PgsqlService {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		System.out.println(indexParse("C:\\logs\\index sample.txt"));
	//exportDDL();
	
	//	Thread.sleep(5000);
		
//		System.out.println("--start getOutputStream write pwd");
//		OutputStream OutputStream1 = Process1.getOutputStream();
//		OutputStream1.write("cloudhealth\r\n".getBytes());
//
//	//	Process1.  waitFor();  
//		Global.heartbeatRecycle("heartbeat_str");
//		System.out.println("--f");
	}
	private static char[] indexParse(String string) {
		
		int fromIndex=string.indexOf("USING btree");
		int end=t.indexOf(';', fromIndex);
		String ddl = t.substring(fromIndex, end);
		return ddl;
		
		return null;
	}
	public static String parseDDL(String sqlpath) {
		String t;
		try {
			t = FileUtils.readFileToString(new File(sqlpath));
		} catch (IOException e) {
			 throw new RuntimeException(e);
		}
		int fromIndex=t.indexOf("CREATE TABLE");
		int end=t.indexOf(';', fromIndex);
		String ddl = t.substring(fromIndex, end);
		return ddl;
	}
	public static void exportDDL(String tab,String outSqlFileName)   {
		String pumd=" C:\\PostgreSQL10bin\\pg_dump.exe  -U postgres -h 192.168.1.18 -d cloudhealth_zq0327  -t aaa1 -s -f  aaa1bek.sql ";
		
		pumd=" C:\\PostgreSQL10bin\\pg_dump.exe   -t @tab@ -s -f \"@outSqlFileName@\"  \"host=192.168.1.18 hostaddr=192.168.1.18 port=5432 user=postgres password=cloudhealth dbname=cloudhealth_zq0327\" ";
		pumd = pumd.replaceAll("@tab@", tab);
		pumd = pumd.replaceAll("@outSqlFileName@", outSqlFileName);
		//   C:\\PostgreSQL10bin\\pg_dump.exe  "host=127.0.0.1 hostaddr=127.0.0.1 port=5432 user=postgres password=123456 dbname=postgres"
		
//	String cmd_param=pumd.replaceAll("\\\\", "\\\\\\\\" );
//		String cmd = "cmd /c  \" @cmd@ \"";
//		cmd = cmd.replaceAll("@cmd@", cmd_param);
			pumd = pumd.replaceAll("@ts@", timestampUtil.gettimeStamp_millsec_filenameEncode());
			

			String lastcmd=pumd;
			System.out.println(lastcmd);
			Process Process1;
			try {
				Process1 = Runtime.getRuntime().exec(lastcmd);
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
			
		
			AsynUtil.execMeth_Ays(new Runnable() {

				@Override
				public void run() {
					try {
						output_out(Process1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}, "threadName");

			AsynUtil.execMeth_Ays(new Runnable() {

				@Override
				public void run() {
					try {
						outputErr(Process1);
					} catch (IOException e) {

						e.printStackTrace();
					}

				}
			}, "threadName");
	}

	public static void output_out(Process Process1) throws UnsupportedEncodingException, IOException {
		System.out.println("--start  getInputStream output");
		InputStreamReader isr = new InputStreamReader(Process1.getInputStream(), "utf8");
		int i;
		while ((i = isr.read()) != -1) {
			System.out.print((char) i); // 輸出 陳
		}
		System.out.print("\r\n");
		System.out.println("--start  getInputStream output finish");
	}

	public static void outputErr(Process Process1) throws UnsupportedEncodingException, IOException {
		System.out.println("--start  getErrorStream output");
		InputStreamReader isr_err = new InputStreamReader(Process1.getErrorStream(), "gbk");
		int i2;
		while ((i2 = isr_err.read()) != -1) {
			System.out.print((char) i2); // 輸出 陳
		}
		System.out.print("\r\n");
		System.out.println("--start  getErrorStream   finish");
	}

}
