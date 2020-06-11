package com.attilax.coreLuni.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

import com.attilax.oplog.AsynUtil;
import com.attilax.shell.ProcessUtil;
import com.attilax.str.Strutil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CliService {

	public static void main(String[] args) throws IOException, InterruptedException {
		// t();

		String lastcmd = "cmd /c dir c:\\0logs";
		lastcmd="systeminfo | find \"内存\"";
		lastcmd=" cmd /c   dir    phpshell.php  /s";
		lastcmd=" cmd.exe /c dir log4j*.proper* /s";
	//	lastcmd="notepad";//if notepad 
		String charsetName = "utf8";charsetName = "gbk";
	String cwd="c:/0logs";//cwd=null;
		// Process Process1 = Runtime.getRuntime().exec(lastcmd);
	
	
	////if notepad   then ok ..if dir then fail
//		Process 	Process1=  new ProcessBuilder(lastcmd).directory(new File(cwd)).start();
	
	
	
	
		Process 	Process1=ProcessUtil.start(lastcmd,cwd);  
	//	Thread.sleep(1000);
		String stdout_2str_ByIoutil = CliService.stdout_2str_ByIoutil(Process1, charsetName);
		System.out.println(stdout_2str_ByIoutil);
		System.out.println("---------errout::is now");
		 String erroutput2strByIoutil = Erroutput2strByIoutil(Process1, charsetName);
		System.out.println(erroutput2strByIoutil);
		
		System.out.println("--f");
	}

	private static  String output_out_2str_recyVar(Process process1, String charsetName) {
		List li=Lists.newArrayList();
		System.out.println("--start  getInputStream output");
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(process1.getInputStream(), "utf8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();throw new RuntimeException(e);
		}
		int i;
		try {
			while ((i = isr.read()) != -1) {
				System.out.print((char) i); // 輸出 陳
				li.add((char) i);
			}
		} catch (IOException e) {
			 
			e.printStackTrace();throw new RuntimeException(e);
		}
		System.out.print("\r\n");
		System.out.println("--start  getInputStream output finish");
		String join = Joiner.on("").join(li);
		return    join;   
	}

	public static String stdout_2str_ByIoutil(Process Process1, String charsetName) {
		System.out.println("--start  getInputStream output");
		InputStreamReader isr;

		try {
			isr = new InputStreamReader(Process1.getInputStream(), charsetName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		String string = null;
		try {
			 string = IOUtils.toString(isr);
		//List<String> li=	IOUtils.readLines(isr);
		//string = Joiner.on("\r\n").join(li);  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return string;

	}

	
	public static String Erroutput2strByIoutil(Process Process1, String charsetName)  {
		System.out.println("--start  getErrorStream output");
		InputStreamReader isr_err;String string;
		try {
			isr_err = new InputStreamReader(Process1.getErrorStream(), charsetName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();throw new RuntimeException(e);
		}
		try {
			  string = IOUtils.toString(isr_err);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();throw new RuntimeException(e);
		}
		return string;
	}

	
	
	public static String outputErr2str(Process Process1) throws UnsupportedEncodingException, IOException {
		System.out.println("--start  getErrorStream output");
		InputStreamReader isr_err = new InputStreamReader(Process1.getErrorStream(), "gbk");
		int i2;
		while ((i2 = isr_err.read()) != -1) {
			System.out.print((char) i2); // 輸出 陳
		}
		System.out.print("\r\n");
		System.out.println("--start  getErrorStream   finish");
		return null;
	}

	@Deprecated
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
	@Deprecated
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

	public static Process start(String cmd, String cwd) {
		// TODO Auto-generated method stub
		return ProcessUtil.start(cmd, cwd);
	}

}
