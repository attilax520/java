package com.attilax.autoup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.NotFoundException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.attilax.compress.ZipUtil;
import com.attilax.core.AsynUtil;
import com.attilax.core.Consumer;
import com.attilax.core.functionImp;
import com.attilax.io.pathx;
import com.attilax.net.ftp.RunnableImp;
import com.attilax.net.ftp.lambdaRunner;
import com.attilax.shell.CliShellProcessService;
import com.attilax.shell.ProcessUtil;
import com.attilax.util.CliService;
import com.attilax.util.ExUtil;
import com.attilax.util.Global;
import com.attilax.util.cli.SSHHelper;
import com.attilax.util.cli.cliRet;
import com.attilax.web.respService;
import com.cnhis.cloudhealth.module.log.Cls1;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

// com.attilax.autoup.autoupdate  getRztByContainid
public class autoupdate {

	public static void main(String[] args) {
		// up

		String zipFilePath = "c:\\0000upzip\\firdir.zip   ";
		zipFilePath = "c:\\0000upzip\\honurse WebContent v2 s510.zip";
		zipFilePath = "c:\\d\\dd2\\up511.zip";
		try {
			List<String> ret = new autoupdate().exeup(zipFilePath, "utf8");
			System.out.println(ret);
		} catch (Exception e) {
			System.out.println(JSON.toJSONString(e));
		}

		System.out.println("--f");

	}

	String upziprztlog = "";
	@SuppressWarnings("all")
	public Map debuginfo = Maps.newConcurrentMap();
	Object ret_exeUpdate;
	// boolean show_out_timer_enable = true;
	public SSHHelper SSHHelper1;

	@Test
	public void test_exe_V2() throws Exception {
		final java.lang.String rztContainId = "1213";
		final java.lang.String zipFilePath = "c:\\home\\cnhis\\cnhisShell\\1.0.102.20180613.zip";
		final java.lang.String jarupzippath = "c:\\home\\cnhis\\cnhisShell";
		final autoupdate autoupdate1 = new autoupdate();

		// sub thread show output
		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {
				try {
					ret_exeUpdate = autoupdate1.exeUpdate(zipFilePath, "gbk", jarupzippath, rztContainId);
					Global.map.put("cli_finishstat_" + rztContainId, true);

					System.out.println("-----------------finish autoupdate1.exeUpdate");

				} catch (Exception e) {
					ExUtil.throwExV2(e);
				}

			}

		}, "threadName_readrzt");

		//
		// // Thread.sleep(2000);// wait other output theard output all
		// // show_out_timer_enable = false;
		// // last get rzt then ouput exeUpdate_rzt and close all down..stop
		// main
		// // thread
		// List li2_last = getRztByContainid(rztContainId);
		// if (li2_last.size() > 0) {
		// String out = "\r\n" + Joiner.on("\r\n").join(li2_last);
		// System.out.println(out);
		// }
		//

		while (true) {
			List li = null;

			li = getRztByContainid(rztContainId);

			if (li.size() > 0) {
				String out = "\r\n" + Joiner.on("\r\n").join(li);
				System.out.println(out);
			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			boolean finished = (boolean) Global.map.get("cli_finishstat_" + rztContainId);
			if (finished) // finish
			{
				// last one
				li = getRztByContainid(rztContainId);

				if (li.size() > 0) {
					String out = "\r\n" + Joiner.on("\r\n").join(li);
					System.out.println(out);
				}
				System.out.println("exeUpdate rzt:" + ret_exeUpdate);
				break;
			}

		}

	}

	@Test
	public void test_exe() throws Exception {
		final java.lang.String rztContainId = "1213";
		final java.lang.String zipFilePath = "c:\\home\\cnhis\\cnhisShell\\1.0.102.20180613.zip";
		final java.lang.String jarupzippath = "c:\\home\\cnhis\\cnhisShell";
		final autoupdate autoupdate1 = new autoupdate();

		// sub thread show output
		AsynUtil.execMeth_Ays(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						List li = null;

						li = getRztByContainid(rztContainId);

						if (li.size() > 0) {
							String out = "\r\n" + Joiner.on("\r\n").join(li);
							System.out.println(out);
						}

						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						boolean finished = (boolean) Global.map.get("cli_finishstat_" + rztContainId);
						if (finished) // finish
						{

							break;
						}

					}
				} catch (Exception e) {
					ExUtil.throwExV2(e);
				}

			}

		}, "threadName_readrzt");

		ret_exeUpdate = autoupdate1.exeUpdate(zipFilePath, "gbk", jarupzippath, rztContainId);
		Global.map.put("cli_finishstat_" + rztContainId, true);
		System.out.println("-----------------finish autoupdate1.exeUpdate");
		Thread.sleep(2000);// wait other output theard output all
		// show_out_timer_enable = false;
		// last get rzt then ouput exeUpdate_rzt and close all down..stop main
		// thread
		List li2_last = getRztByContainid(rztContainId);
		if (li2_last.size() > 0) {
			String out = "\r\n" + Joiner.on("\r\n").join(li2_last);
			System.out.println(out);
		}

		System.out.println("exeUpdate rzt:" + ret_exeUpdate);

	}

	public List<java.lang.String> getRztByContainid(final java.lang.String rztContainId) throws InterruptedException {
		while (true) {
			List<java.lang.String> rztByContainid_baselayer = getRztByContainid_baselayer(rztContainId);

			if (rztByContainid_baselayer.size() > 0)
				return rztByContainid_baselayer;

			// else ==0

			boolean finished = (boolean) Global.map.get("cli_finishstat_" + rztContainId);
			if (finished) // finish
			{
				// break;
				return rztByContainid_baselayer;

			}
			Thread.sleep(500);
			continue;

		}

	}

	private List<java.lang.String> getRztByContainid_baselayer(final java.lang.String rztContainId) {
		List<String> ret_cmd_rzt_li2 = Lists.newArrayList();
		List<String> ret_cmd_rzt_li = (List<java.lang.String>) Global.map.get(rztContainId);

		if (ret_cmd_rzt_li != null && ret_cmd_rzt_li.size() > 0) {

			ret_cmd_rzt_li2.addAll(ret_cmd_rzt_li);
			// System.out.println(ret_cmd_rzt_li);
			ret_cmd_rzt_li.clear();
			return ret_cmd_rzt_li2;

		}
		return ret_cmd_rzt_li2;
	}

	public static Logger logger = Logger.getLogger(autoupdate.class);

	
	@Test
	public void test_exeUpdate_rmt_Linux_V2()
	{
		
		java.lang.String svrinfo="192.168.1.18,root,cloudhealth,linux";
		String[] a=svrinfo.trim().split(",");
		SSHHelper SSHHelper1 = null;
		 
			try {
				SSHHelper1 = new SSHHelper(a[0], 22, a[1], a[2]);
			} catch (Exception e1) {
				ExUtil.throwExV2(e1);
			}
		 
			
			
		final autoupdate autoupdate1 = new autoupdate();
		autoupdate1.SSHHelper1=SSHHelper1;
		java.lang.String upShellDir="/0install";
		java.lang.String charset_console_ret="utf8";
		final java.lang.String rztContainId="123";
		
		Timer tmr=new Timer();
		tmr.schedule(new TimerTask() {
			
			@Override
			public void run() {
			List li = null;
			try {
				li = autoupdate1.getRztByContainid(rztContainId);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(li);
				
				
			}
		}, 100, 500);
		 
		
		try {
			autoupdate1.exeUpdate_rmt_Linux_V2(upShellDir, charset_console_ret, rztContainId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("--f");
	}
	/**
	 * rmt execute
	 * 
	 * @param zipFilePath
	 * @param charset_console_ret
	 * @param upShellDir
	 * @param rztContainId
	 * @return
	 * @throws Exception
	 */
	public Object exeUpdate_rmt_Linux_V2(java.lang.String upShellDir, java.lang.String charset_console_ret,
			java.lang.String rztContainId) throws Exception {
		Global.map.put("cli_finishstat_" + rztContainId, false);
		final List<String> ret_cmd_rzt_li = Lists.newArrayList();
		Global.map.put(rztContainId, ret_cmd_rzt_li);

 
		String update_file = upShellDir + "/update.sh";
	 

		setAuthChmod_rmt(upShellDir, charset_console_ret, ret_cmd_rzt_li, update_file);

		// debuginfo.put("cmd", lastcmd);
		ret_cmd_rzt_li.add(">>>>>>" + update_file);
		
		
		
		SSHHelper1.sendCmdV2(update_file, new Consumer<String>() {

			@Override
			public void accept(String ret_line) throws Exception {
				ret_cmd_rzt_li.add(ret_line);

			}
		});

	 
		Global.map.put("cli_finishstat_" + rztContainId, true);
		return new Object();
		// ret_cmd_rzt_li;

	}

	private void setAuthChmod_rmt(java.lang.String upShellDir, java.lang.String charset_console_ret,
			final List<String> ret_cmd_rzt_li, String lastcmd) throws Exception {
		// add shell file auth

		String chAuthCmd = " chmod 777  " + lastcmd;
		ret_cmd_rzt_li.add(">>>>>>" + chAuthCmd);
	cliRet cr=	SSHHelper1.sendCmd_retImdtly(chAuthCmd );
	System.err.println( JSON.toJSONString(cr));

	//	SSHHelper1.close();

	}

	private void setAuthChmod(java.lang.String upShellDir, java.lang.String charset_console_ret,
			final List<String> ret_cmd_rzt_li, String lastcmd) {
		// add shell file auth

		String chAuthCmd = " chmod 777  " + lastcmd;
		ret_cmd_rzt_li.add(">>>>>>" + chAuthCmd);
		Process Process1 = ProcessUtil.start(chAuthCmd, upShellDir);
		// Thread.sleep(1000);
		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charset_console_ret);
		ret_cmd_rzt_li.add(stdout_2str_ByIoutil);
		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charset_console_ret);
		if (erroutput2strByIoutil.trim().length() > 10) {
			// debuginfo.put("chmod ret ", stdout_2str_ByIoutil);
			// ret_cmd_rzt_li.add(">>>>>>chmod ret:");
			ret_cmd_rzt_li.add(erroutput2strByIoutil);
			ExUtil.throwExV4("chmod 777 ex,file；" + lastcmd, ret_cmd_rzt_li);

		}
	}

	/**
	 * s620
	 * 
	 * @param zipFilePath
	 * @param charset_console_ret
	 * @param upShellDir
	 * @param rztContainId
	 * @return
	 * @throws Exception
	 */
	Object exeUpdate(java.lang.String zipFilePath, java.lang.String charset_console_ret, java.lang.String upShellDir,
			java.lang.String rztContainId) throws Exception {
		Global.map.put("cli_finishstat_" + rztContainId, false);
		final List<String> ret_cmd_rzt_li = Lists.newArrayList();
		Global.map.put(rztContainId, ret_cmd_rzt_li);

		String shellext = pathx.shellex();
		String lastcmd = upShellDir + File.separator + "update." + shellext;
		if (!new File(lastcmd).exists())
			ExUtil.throwExV4("notexistex,file:" + lastcmd, ret_cmd_rzt_li);

		if (pathx.isLinux()) {
			// add shell file auth

			setAuthChmod(upShellDir, charset_console_ret, ret_cmd_rzt_li, lastcmd);

		}
		// debuginfo.put("cmd", lastcmd);
		ret_cmd_rzt_li.add(">>>>>>" + lastcmd);

		Map exe_rzt = new CliService().exe(lastcmd, new Consumer<String>() {

			@Override
			public void accept(String line) throws Exception {
				// System.out.println(line);
				ret_cmd_rzt_li.add(line);

			}
		}, charset_console_ret);
		Global.map.put("cli_finishstat_" + rztContainId, true);
		return exe_rzt;
		// ret_cmd_rzt_li;

	}

	public List<String> exe(final String zipFilePath, String charset_console_ret, final String upShellDir) {
		final List<String> ret_cmd_rzt_li = Lists.newArrayList();

		String charsetName = charset_console_ret;// charsetName = "gbk";

		String subdir = pathx.filemainname(new File(zipFilePath).getName());
		String shellext = pathx.shellex();
		String lastcmd = upShellDir + File.separator + "update." + shellext;
		if (!new File(lastcmd).exists())
			ExUtil.throwExV4("notexistex,file:" + lastcmd, ret_cmd_rzt_li);

		if (pathx.isLinux()) {
			// add shell file auth

			setAuthChmod(upShellDir, charsetName, ret_cmd_rzt_li, lastcmd);

		}
		// debuginfo.put("cmd", lastcmd);
		ret_cmd_rzt_li.add(">>>>>>" + lastcmd);

		Process Process1 = ProcessUtil.start(lastcmd, upShellDir);
		// Thread.sleep(1000);
		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
		ret_cmd_rzt_li.add(stdout_2str_ByIoutil);
		// System.out.println(stdout_2str_ByIoutil);
		// System.out.println("---------errout::is now错误信息");
		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
		ret_cmd_rzt_li.add(erroutput2strByIoutil);
		// System.out.println(erroutput2strByIoutil);
		// String ret = Joiner.on("\r\n").join(ret_cmd_rzt_li);
		return ret_cmd_rzt_li;
	}

	List<String> exeup(final String zipFilePath, String charset_console_ret, final String upzippath) {
		final List<String> ret_cmd_rzt_li = Lists.newArrayList();
		ZipUtil.unzipV3(zipFilePath, upzippath, new Function() {

			@Override
			public Object apply(Object input) {
				ret_cmd_rzt_li.add("upzip file准备解压文件:" + input);

				String zipfileDir = new File(zipFilePath).getParent();

				ret_cmd_rzt_li.add("upzip file to dst解压文件到目录:" + upzippath + File.separator + input);
				return null;
			}
		});
		debuginfo.put("ret_cmd_rzt_li", ret_cmd_rzt_li);

		String opdir = (upzippath);// new File(zipFilePath).getParent();

		String charsetName = charset_console_ret;// charsetName = "gbk";

		String subdir = pathx.filemainname(new File(zipFilePath).getName());
		String shellext = pathx.shellex();
		String lastcmd = opdir + File.separator + subdir + File.separator + "update." + shellext;
		if (!new File(lastcmd).exists())
			ExUtil.throwExV4("notexistex,file:" + lastcmd, ret_cmd_rzt_li);

		if (pathx.isLinux()) {
			// add shell file auth

			setAuthChmod(opdir, charsetName, ret_cmd_rzt_li, lastcmd);

		}
		// debuginfo.put("cmd", lastcmd);
		ret_cmd_rzt_li.add(">>>>>>" + lastcmd);

		Process Process1 = ProcessUtil.start(lastcmd, opdir);
		// Thread.sleep(1000);
		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
		ret_cmd_rzt_li.add(stdout_2str_ByIoutil);
		// System.out.println(stdout_2str_ByIoutil);
		// System.out.println("---------errout::is now错误信息");
		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
		ret_cmd_rzt_li.add(erroutput2strByIoutil);
		// System.out.println(erroutput2strByIoutil);
		// String ret = Joiner.on("\r\n").join(ret_cmd_rzt_li);
		return ret_cmd_rzt_li;
	}

	private Object String(String upzippath) {
		// TODO Auto-generated method stub
		return null;
	}

	List<String> exeup(final String zipFilePath, String charset_console_ret) {
		final List<String> ret_cmd_rzt_li = Lists.newArrayList();
		ZipUtil.unzipZipoisit(zipFilePath, new Function() {

			@Override
			public Object apply(Object input) {
				ret_cmd_rzt_li.add("upzip file准备解压文件:" + input);

				String zipfileDir = new File(zipFilePath).getParent();

				ret_cmd_rzt_li.add("upzip file to dst解压文件到当前目录:" + zipfileDir + "/" + input);
				return null;
			}
		});
		debuginfo.put("ret_cmd_rzt_li", ret_cmd_rzt_li);

		String opdir = new File(zipFilePath).getParent();

		String charsetName = charset_console_ret;// charsetName = "gbk";

		String subdir = pathx.filemainname(new File(zipFilePath).getName());
		String shellext = pathx.shellex();
		String lastcmd = opdir + File.separator + subdir + File.separator + "update." + shellext;
		if (!new File(lastcmd).exists())
			ExUtil.throwExV4("notexistex,file:" + lastcmd, ret_cmd_rzt_li);

		if (pathx.isLinux()) {
			// add shell file auth

			setAuthChmod(opdir, charsetName, ret_cmd_rzt_li, lastcmd);

		}
		// debuginfo.put("cmd", lastcmd);
		ret_cmd_rzt_li.add(">>>>>>" + lastcmd);

		Process Process1 = ProcessUtil.start(lastcmd, opdir);
		// Thread.sleep(1000);
		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
		ret_cmd_rzt_li.add(stdout_2str_ByIoutil);
		// System.out.println(stdout_2str_ByIoutil);
		// System.out.println("---------errout::is now错误信息");
		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
		ret_cmd_rzt_li.add(erroutput2strByIoutil);
		// System.out.println(erroutput2strByIoutil);
		// String ret = Joiner.on("\r\n").join(ret_cmd_rzt_li);
		return ret_cmd_rzt_li;
	}

}
