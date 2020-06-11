package com.attilax.device;

import java.util.Arrays;
import java.util.List;

import com.attilax.shell.CliShellProcessService;
import com.attilax.shell.ProcessUtil;
import com.attilax.util.Listutil;

public class cpuMonitorWin {

	public static cpuMonitorWin $() {
		// TODO Auto-generated method stub
		return new cpuMonitorWin();
	}

 
	
	Object cpuuseWin() {
		String charsetName = "utf8";
		charsetName = "gbk";
		String cmd = "cmd /c  \"wmic cpu get loadpercentage\" ";
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
		return cpuuseWin(stdout_2str_ByIoutil);
	}

	private Object cpuuseWin(String stdout_2str_ByIoutil) {
		String[] sa=stdout_2str_ByIoutil.split("\r\n");
		List<String> stringA = Arrays.asList(sa);
		stringA=Listutil.delEmptyElement(stringA);
		stringA=Listutil.trimElement(stringA);
		return stringA.get(stringA.size()-1);
	}

}
