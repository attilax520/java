package com.attilax.device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.attilax.collection.mapBuilder;
import com.attilax.core.Consumer;
import com.attilax.data.csv.csvService;
import com.attilax.io.pathx;
import com.attilax.shell.CliShellProcessService;
import com.attilax.shell.ProcessUtil;
import com.attilax.str.strService;
import com.attilax.util.Listutil;
import com.attilax.util.cli.SSHHelper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;


// com.attilax.device.perfMonitorServ
public class perfMonitorServ {
	
	
	public static Logger logger = LoggerFactory.getLogger(perfMonitorServ.class);

	public static void main(String[] args) throws Exception {
		String linux_password = "cloudhealth";
		Map m=mapBuilder.$().put("os","linux").put("host", "192.168.1.18").put("user", "root").put("pwd", linux_password).build();
		System.out.println(new perfMonitorServ().sysinfo(m));
		// System.out.println( new cpuservice().memusage(null));
		// System.out.println( new perfMonitorService().ioinfo(null));

		System.out.println(new ArrayList() {
			{
				this.add(new HashMap() {
					{
						this.put("k", "");
					}
				});
				this.add(new HashMap() {
					{
						this.put("y", "");
					}
				});
			}
		});
	}

	@SuppressWarnings("all")
	public Object ioinfo(Object object) {
		if(pathx.isWindows())
			return ioinfo_win();
		else
			return ioinfoLinux();
	
	}

	public Object ioinfo_win() {
		String cmd = "wmic path Win32_PerfFormattedData_PerfDisk_LogicalDisk where Name='_Total' get DiskBytesPersec,DiskReadBytesPersec,DiskWriteBytesPersec,DiskTransfersPersec,DiskReadsPersec,DiskWritesPersec,PercentDiskTime ,PercentDiskReadTime ,PercentDiskWriteTime";

		String charsetName = "utf8";
		charsetName = "gbk";

		Process Process1 = ProcessUtil.start(cmd, null);

		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
		System.out.println(erroutput2strByIoutil);

		List<Map> tab = csvService.toTableByTab(stdout_2str_ByIoutil);
		List<Map> tab2 = Listutil.from(tab).select(
				"PercentDiskTime,DiskReadBytesPersec,DiskWriteBytesPersec,,DiskReadsPersec,DiskWritesPersec,DiskBytesPersec,DiskTransfersPersec, ,PercentDiskReadTime ,PercentDiskWriteTime");
		// return li.get(li.size()-1).trim();
		return tab2.get(0);
	}

	public Object ioinfoLinux() {
		String charsetName = "utf8";
		charsetName = "gbk";

		String cmd = " iostat -x ";
		Process Process1 = ProcessUtil.start(cmd, null);
		String t = CliShellProcessService.getOutput(cmd, charsetName);
		return ioinfoLinuxParseCmdRetTxt(t);

	}

	public Object ioinfoLinuxParseCmdRetTxt(String t) {
	//	try {
			String[] a = t.split("\n");
			int pctStat = 0;
			for (String line : a) {
				if (line.contains("svctm")) {
					int stat = line.indexOf("svctm");
					pctStat = stat + 6;
					continue;
				}
				if (line.startsWith("sda")) {
					String pct = line.substring(pctStat);
					pct = pct.trim();
					Map m = mapBuilder.$().put("PercentDiskTime", pct).build();
					return m;
				}
			}
//		} catch (Exception e) {
//			throw new RuntimeException("ininfoLinuxParseCmdRetTxt ex", e);
//		}
		throw new RuntimeException("ininfoLinuxParseCmdRetTxt ex");

	}




	public Object sysinfo_curpc() throws Exception {

		logger.info("sysinfo");

		if (pathx.isWindows()) {
			Object ioinfo = new perfMonitorServ().ioinfo(null);
			Map m = mapBuilder.$().put("cpu",cpuMonitorWin.$().cpuuseWin()).put("mem", memMonitoWinr.$().memusage(null)).put("io", ioinfo).build();
			return m;
		} else {
			return sysinfo_linux();
		}
	}



	private Object sysinfo_linux() {

		Object ioinfo = null;
		 
		try {
			ioinfo = new perfMonitorServ().ioinfoLinux();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object cpuuseLinux = null;
		;
		try {
			cpuuseLinux = cpuuseLinux();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Object memusageLinux = null;
		try {
			memusageLinux =memMonitorLinux.$().memusageLinux();
			logger.info("memusageLinux:" + memusageLinux);
		} catch (Exception e) {
			memusageLinux = mapBuilder.$().put("canuse", 50).put("all", 100).build();

			logger.error("memusageLinux ex", e);
			// cpuuseLinux=
		}

		return mapBuilder.$().put("cpu", cpuuseLinux).put("mem", memusageLinux).put("io", ioinfo).build();
	}

	public Object sysinfo(Map m2) throws Exception {

		logger.info("sysinfo");

		if (pathx.isWindows()) {
			Object ioinfo = new perfMonitorServ().ioinfo(null);
			Map m = mapBuilder.$().put("cpu",cpuMonitorWin.$().cpuuseWin()).put("mem", memMonitoWinr.$().memusage(null)).put("io", ioinfo).build();
			return m;
		} else {
			return sysinfo_linux(m2);
		}

	}
	
	
	public Object sysinfoV2(Map m2) throws Exception {

		logger.info("sysinfo");

		if (m2.get("os").toString().equals("win")) {
			Object ioinfo = new perfMonitorServ().ioinfo(null);
			Map m = mapBuilder.$().put("cpu",cpuMonitorWin.$().cpuuseWin()).put("mem", memMonitoWinr.$().memusage(null)).put("io", ioinfo).build();
			return m;
		} else { //linux
			return sysinfo_linux(m2);
		}

	}

	/**
	 *  remt linux mode...
	 * @param m2
	 * @return
	 * @throws Exception 
	 */
	private Object sysinfo_linux(Map m2) throws Exception {
		Object ioinfo = null;
		SSHHelper helper = new SSHHelper(m2.get("host").toString(), 22,m2.get("user").toString(), m2.get("pwd").toString());
	 
		try {
			ioinfo = new perfMonitorServ().ioinfoLinux(helper);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object cpuuseLinux = null;
		;
		try {
			cpuuseLinux = cpuuseLinux(helper);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Object memusageLinux = null;
		try {
			memusageLinux =memMonitorLinux.$().memusageLinux_rmt(helper);
			logger.info("memusageLinux:" + memusageLinux);
		} catch (Exception e) {
			memusageLinux = mapBuilder.$().put("canuse", 50).put("all", 100).build();

			logger.error("memusageLinux ex", e);
			// cpuuseLinux=
		}

		return mapBuilder.$().put("cpu", cpuuseLinux).put("mem", memusageLinux).put("io", ioinfo).build();
	}
	private Object cpuuseLinux(SSHHelper helper) throws Exception {
		String charsetName = "utf8";
		charsetName = "gbk";
		String cmd = "top -bn 1 "; 	Process Process1 = ProcessUtil.start(cmd, null);
	 
	//	SSHHelper helper = new SSHHelper(m2.get("host").toString(), 22,m2.get("user").toString(), m2.get("pwd").toString());

		
		String t = helper.getCmdRet(cmd );
		
		String stdout_2str_ByIoutil =  t;
		return cpuuseLinux(stdout_2str_ByIoutil);
	}

 

	private Object ioinfoLinux(SSHHelper helper) throws Exception {
		String charsetName = "utf8";
		charsetName = "gbk";
		
		


		String cmd = " iostat -x ";
		
		final List<String> li=Lists.newArrayList();
		helper.sendCmdV2(cmd, new Consumer<String>() {

			@Override
			public void accept(String t) throws Exception {
				li.add(t);
				
			}
		});
		String t = Joiner.on("\n").join(li);
		return ioinfoLinuxParseCmdRetTxt(t);
	}

	Object cpuuse() {
		if(pathx.isWindows())
		return  cpuMonitorWin.$(). cpuuseWin();
		else
			return cpuuseLinux();
		
	}
	// C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\device\\toprzt.txt
	

	private Object cpuuseLinux() {
		String charsetName = "utf8";
		charsetName = "gbk";
		String cmd = "top -bn 1 ";
		Process Process1 = ProcessUtil.start(cmd, null);
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
		System.out.println(erroutput2strByIoutil);
		logger.info("erroutput2strByIoutil:" + erroutput2strByIoutil);
		return cpuuseLinux(stdout_2str_ByIoutil);
	}

	public Object cpuuseLinux(String stdout_2str_ByIoutil) {
		logger.info("stdout_2str_ByIoutil:" + stdout_2str_ByIoutil);
		String[] sa = stdout_2str_ByIoutil.split("\n");

		logger.info("stdout_2str_ByIoutil.split(@n)   sa.length:" + sa.length);
		List<String> stringA = Arrays.asList(sa);
		List<String> li = Listutil.delEmptyElement(stringA);
		for (String string : li) {
			string = string.trim();
			if (string.trim().toLowerCase().startsWith("%cpu")) {
				int stat = string.indexOf(":");
				int end = string.indexOf(".");
				String use = string.substring(stat + 1, end);
				use = use.trim();
				return use;
			}
		}

		return 0;
	}

	// public Object cpuuseLinux(String stdout_2str_ByIoutil) {
	// String[] sa=stdout_2str_ByIoutil.split("\r\n");
	// List<String> stringA = Arrays.asList(sa);
	// List<String> li= Listutil.delEmptyElement(stringA);
	// return li.get(li.size()-1).trim();
	// }

}
