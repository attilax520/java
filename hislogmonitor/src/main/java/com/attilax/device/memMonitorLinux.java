package com.attilax.device;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.attilax.collection.mapBuilder;
import com.attilax.shell.CliShellProcessService;
import com.attilax.shell.ProcessUtil;
import com.attilax.str.strService;
import com.attilax.util.Listutil;
import com.attilax.util.cli.SSHHelper;

public class memMonitorLinux {

	public static memMonitorLinux $() {
		// TODO Auto-generated method stub
		return new memMonitorLinux();
	}

	public Object memusageLinux() {
		String charsetName = "utf8";
		charsetName = "gbk";
		String cmd = "top -bn 1 ";
		Process Process1 = ProcessUtil.start(cmd, null);

		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
		System.out.println(erroutput2strByIoutil);
		return memuseLinux(stdout_2str_ByIoutil);

	}
	
	public Object memusageLinux_rmt(SSHHelper helper) throws Exception {
		String charsetName = "utf8";
		charsetName = "gbk";
		String cmd = "top -bn 1 ";
	 
		
	//SSHHelper helper = new SSHHelper(helper2.get("host").toString(), 22,helper2.get("user").toString(), helper2.get("pwd").toString());

		
		String stdout_2str_ByIoutil = helper.getCmdRet(cmd );
	 
		return memuseLinux(stdout_2str_ByIoutil);

	}
	
	public Object memusageLinux(Map m) {
		String charsetName = "utf8";
		charsetName = "gbk";
		String cmd = "top -bn 1 ";
	 
		
		
		Process Process1 = ProcessUtil.start(cmd, null);

		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
		System.out.println(erroutput2strByIoutil);
		return memuseLinux(stdout_2str_ByIoutil);

	}

	public Object memuseLinux(String stdout_2str_ByIoutil) {
		String[] sa = stdout_2str_ByIoutil.split("\n");
		List<String> stringA = Arrays.asList(sa);
		List<String> li = Listutil.delEmptyElement(stringA);

		// for (String string : li) {
		// string=string.trim();
		// if(string.trim().toLowerCase().startsWith("cpu"))
		// {
		// int stat=string.indexOf(":");
		// int end=string.indexOf(".");
		// String use=string.substring(stat+1,end);
		// return use;
		// }
		// }

		int memAll = 0;
		int memCanuse = 0;
		for (String line : li) {
			line = line.trim();

			if (line.startsWith("KiB Mem")) {
				memAll = getmemallLinux(line);
				memCanuse = parseCanuseMemLinux(line);
			}
		}

		Map m = mapBuilder.$().put("canuse", memCanuse).put("all", memAll).build();
		return m;
	}

	// Mem: 1939780k total, 1375280k used,
	private int parseCanuseMemLinux(String line) {
		int stat = line.indexOf(",");
		int end = line.indexOf("free", stat + 1);
		String use = line.substring(stat + 1, end);
		use = use.trim();
		int stat2 = strService.indexOf(use, "num");
		use = use.substring(stat2);
		return Integer.parseInt(use);
	}

	// Mem: 1939780k total, 1375280k used,
	private int getmemallLinux(String line) {
		int stat = line.indexOf(":");
		int end = line.indexOf("total");
		String use = line.substring(stat + 1, end);
		use = use.trim();
		int stat2 = strService.indexOf(use, "num");
		use = use.substring(stat2);
		return Integer.parseInt(use);
	}

}
