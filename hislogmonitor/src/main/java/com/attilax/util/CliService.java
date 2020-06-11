package com.attilax.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.attilax.autoup.autoupdate;
import com.attilax.core.Consumer;
import com.attilax.oplog.AsynUtil;
import com.attilax.shell.ProcessUtil;
import com.attilax.str.Strutil;
import com.attilax.util.cli.cliRet;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

//rlt   CliShellProcessService.java
//  com.attilax.util.CliService
public class CliService {

	public static void main(String[] args) throws IOException, InterruptedException {
		// t();

		String lastcmd = "cmd /c dir c:\\0logs";
		lastcmd = "systeminfo | find \"内存\"";
		lastcmd = " cmd /c   dir    phpshell.php  /s";
		lastcmd = " cmd.exe /c dir log4j*.proper* /s";
		// lastcmd="notepad";//if notepad
		String charsetName = "utf8";
		charsetName = "gbk";
		String cwd = "c:/0logs";// cwd=null;
		// Process Process1 = Runtime.getRuntime().exec(lastcmd);

		//// if notepad then ok ..if dir then fail
		// Process Process1= new ProcessBuilder(lastcmd).directory(new
		// File(cwd)).start();

		Process Process1 = ProcessUtil.start(lastcmd, cwd);
		// Thread.sleep(1000);
		String stdout_2str_ByIoutil = CliService.stdout_2str_ByIoutil(Process1, charsetName);
		System.out.println(stdout_2str_ByIoutil);
		System.out.println("---------errout::is now");
		String erroutput2strByIoutil = Erroutput2strByIoutil(Process1, charsetName);
		System.out.println(erroutput2strByIoutil);

		System.out.println("--f");
	}

	private static String output_out_2str_recyVar(Process process1, String charsetName) {
		List li = Lists.newArrayList();
		System.out.println("--start  getInputStream output");
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(process1.getInputStream(), "utf8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		int i;
		try {
			while ((i = isr.read()) != -1) {
				System.out.print((char) i); // 輸出 陳
				li.add((char) i);
			}
		} catch (IOException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		System.out.print("\r\n");
		System.out.println("--start  getInputStream output finish");
		String join = Joiner.on("").join(li);
		return join;
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
			// List<String> li= IOUtils.readLines(isr);
			// string = Joiner.on("\r\n").join(li);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return string;

	}

	public static String Erroutput2strByIoutil(Process Process1, String charsetName) {
		System.out.println("--start  getErrorStream output");
		InputStreamReader isr_err;
		String string;
		try {
			isr_err = new InputStreamReader(Process1.getErrorStream(), charsetName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			string = IOUtils.toString(isr_err);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
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

	@Test
	public void test_exe() throws Exception {
		String cmd = "ping -t www.baidu.com";
		cmd = "ping -n 10 www.baidu.com";
		Object rzt = exe(cmd, new Consumer<String>() {

			@Override
			public void accept(String line) throws Exception {
				System.out.println(line);

			}
		});
		System.out.println(rzt);
	}

	public static Logger logger = Logger.getLogger(CliService.class);

	public cliRet exe(java.lang.String lastcmd, java.lang.String console_charset) throws Exception {
		final CommandLine cmdLine = CommandLine.parse(lastcmd);
		 return exe_blockAllMode(cmdLine, console_charset);
	}

	public Map exe(java.lang.String lastcmd, Consumer<java.lang.String> consumer, java.lang.String console_charset)
			throws Exception {
		final CommandLine cmdLine = CommandLine.parse(lastcmd);
		return exec(cmdLine, consumer, console_charset);
	}

	@Deprecated
	public Map exe(String cmd, Consumer<String> consumer) throws Exception {

		final CommandLine cmdLine = CommandLine.parse(cmd);
		return exec(cmdLine, consumer);

	}

	@Deprecated
	public Map exec(final CommandLine cmdLine, Consumer<String> consumer)
			throws ExecuteException, IOException, UnsupportedEncodingException, InterruptedException, Exception {

		String charsetName_console = "gbk";
		return exec(cmdLine, consumer, charsetName_console);
	}

	private cliRet exe_blockAllMode(final CommandLine cmdLine, String charsetName_console) throws Exception {

		// final DefaultExecuteResultHandler resultHandler = new
		// DefaultExecuteResultHandler();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		DefaultExecutor executor = new DefaultExecutor();
		// int exitValue = executor.execute(cmdLine);
		executor.setStreamHandler(new PumpStreamHandler(baos, baos));
		int exeRet=executor.execute(cmdLine);
		// 这里开始的代码会被立即执行下去，因为上面的语句不会被阻塞。

		// resultHandler.waitFor(2000);//等待5秒。
		System.out.println("--aft waitFor");

		String result2 = baos.toString(charsetName_console).trim();
	 
		// Thread.sleep(2000);
		 
	 cliRet cr=new cliRet();
	 cr.exitStatCode=exeRet;
	 cr.stdStr=result2;
	 
		return  cr;

 

	}

	private Map exec(final CommandLine cmdLine, Consumer<String> consumer, String charsetName_console)
			throws Exception {

		final DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		DefaultExecutor executor = new DefaultExecutor();
		// int exitValue = executor.execute(cmdLine);
		executor.setStreamHandler(new PumpStreamHandler(baos, baos));
		executor.execute(cmdLine, resultHandler);
		// 这里开始的代码会被立即执行下去，因为上面的语句不会被阻塞。

		// resultHandler.waitFor(2000);//等待5秒。
		System.out.println("--aft waitFor");
		while (true) {
			boolean hasResult = resultHandler.hasResult();
			// must exe finish ,,then hasrestult...yaos belo ,zo ma ..
			// System.out.println("hasResult:" + hasResult);

			String result2 = baos.toString(charsetName_console).trim();
			// logger.info(" ByteArrayOutputStream to str: "+result2);
			int millis_sleep = 500;
			if (result2.length() == 0) {
				baos.reset();
				Thread.sleep(millis_sleep);
				continue;
			}

			baos.reset();
			consumer.accept(result2);
			Thread.sleep(millis_sleep);

			if (hasResult) // if finished
			{
				// Thread.sleep(2000);
				int getExitValue = resultHandler.getExitValue();
				Object ex = resultHandler.getException();
				Map m = Maps.newConcurrentMap();
				m.put("getExitValue", getExitValue);
				if (ex != null)
					m.put("resultHandler getException", ex);
				m.put("t", 1);
				m.put("cmdLine", cmdLine);
				return m; // break;

			}

		}
	}

}
