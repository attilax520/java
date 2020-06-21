package com.kok.sport.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;

 
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * v2 s57
 * @author attilax
 *
 */
public class CliShellProcessService {

	public static void main(String[] args) throws IOException, InterruptedException {
		// t();

		String lastcmd = "cmd /c dir c:\\0logs";
		lastcmd="systeminfo | find \"鍐呭瓨\"";
		lastcmd=" cmd /c   dir    phpshell.php  /s";
		lastcmd=" cmd.exe /c dir log4j*.proper* /s";
		lastcmd="C:\\d\\exesql.bat";
	//	lastcmd="notepad";//if notepad 
		String charsetName = "utf8";
		//charsetName = "gbk";
	String cwd="c:/0logs";//cwd=null;
		// Process Process1 = Runtime.getRuntime().exec(lastcmd);
	
	
	////if notepad   then ok ..if dir then fail
//		Process 	Process1=  new ProcessBuilder(lastcmd).directory(new File(cwd)).start();
	
	
	
	
//	//	Process 	Process1=ProcessUtil.start(lastcmd,cwd);  
//	//	Thread.sleep(1000);
//		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
//		System.out.println(stdout_2str_ByIoutil);
//		System.out.println("---------errout::is now");
//		 String erroutput2strByIoutil = Erroutput2strByIoutil(Process1, charsetName);
//		System.out.println(erroutput2strByIoutil);
		
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
				System.out.print((char) i); // 杓稿嚭 闄�
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

	//todox  need unit test
	public static String getOutput(Process Process1, String charsetName) {
//		final List<String> ret_cmd_rzt_li = Lists.newArrayList();
//		//todox  need unit test
//		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
//		ret_cmd_rzt_li.add(stdout_2str_ByIoutil);
//		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
//		if (erroutput2strByIoutil.trim().length() > 10) {
//			//debuginfo.put("chmod ret ", stdout_2str_ByIoutil);
//			//ret_cmd_rzt_li.add(">>>>>>chmod ret:");
//			ret_cmd_rzt_li.add(erroutput2strByIoutil);
//			Map m=mapBuilder.$().put("stdout", stdout_2str_ByIoutil).put("errout", erroutput2strByIoutil).put("processObj", Process1).build();
//			ExUtil.throwExV4(erroutput2strByIoutil , m);
//			 
//		}
//		
		String ret="";
		return ret;
	}
	
	
	public static String getOutput(String cmd, String charsetName) {
		return charsetName;
		 
		
//		String cwd=null;
//		Process 	Process1=ProcessUtil.start(cmd,cwd);  
//		return getOutput(Process1, charsetName);
		 
	}
		
		
	
	public static String stdout_2str_ByIoutil(Process Process1, String charsetName) {
	//	System.out.println("--start  getInputStream output");
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
		//System.out.println("--start  getErrorStream output");
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
			System.out.print((char) i2); // 杓稿嚭 闄�
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
			System.out.print((char) i); // 杓稿嚭 闄�
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
			System.out.print((char) i2); // 杓稿嚭 闄�
		}
		System.out.print("\r\n");
		System.out.println("--start  getErrorStream   finish");
	}

	public static Process start(String cmd, String cwd) {
		return null;
		// TODO Auto-generated method stub
	//	return ProcessUtil.start(cmd, cwd);
	}

}
