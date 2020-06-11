package com.attilax.device;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.attilax.collection.mapBuilder;
import com.attilax.shell.CliShellProcessService;
import com.attilax.shell.ProcessUtil;
import com.attilax.util.Listutil;

public class memMonitoWinr {

 
	
	
	Object memusage(Object object) {
		String charsetName = "utf8";
		charsetName = "gbk";
		String cmd = " systeminfo   ";
		Process Process1 = ProcessUtil.start(cmd, null);

		String stdout_2str_ByIoutil = CliShellProcessService.stdout_2str_ByIoutil(Process1, charsetName);
		String erroutput2strByIoutil = CliShellProcessService.Erroutput2strByIoutil(Process1, charsetName);
		System.out.println(erroutput2strByIoutil);
		return memusageWin(stdout_2str_ByIoutil);

	}

	private Object memusageWin(String stdout_2str_ByIoutil) {
		String[] sa=stdout_2str_ByIoutil.split("\r\n");
		List<String> stringA = Arrays.asList(sa);
		List<String>  li=	Listutil.delEmptyElement(stringA);
		int memAll = 0;int memCanuse = 0;
		for (String line : li) {
			if(line.contains("物理内存总量"))
				memAll=parseMemall(line);
			
			if(line.contains("可用的物理内存"))
				memCanuse=parseCanuseMem(line);
		}
		int memuse=memAll-memCanuse;
		Double f = (double)memuse/(double)memAll*100f;
		Integer memusePercent=((Double)Math.ceil(f)).intValue();
		Map m=mapBuilder.$().put("canuse", memCanuse).put("all", memAll).build();
		return m;
	}

	public static memMonitoWinr $() {
		// TODO Auto-generated method stub
		return new memMonitoWinr();
	}
	
	private int parseCanuseMem(String line) {
		// 物理内存总量: 8,100 MB
		int start = line.indexOf(":");
		int end = line.indexOf("MB");
		String cnt = line.substring(start + 1, end);
		cnt = cnt.trim().replaceAll(",", "");
		return Integer.parseInt(cnt);
	}
// for win
	private int parseMemall(String line) {
		// 物理内存总量: 8,100 MB
		int start = line.indexOf(":");
		int end = line.indexOf("MB");
		String cnt = line.substring(start + 1, end);
		cnt = cnt.trim().replaceAll(",", "");
		return Integer.parseInt(cnt);
	}

}
