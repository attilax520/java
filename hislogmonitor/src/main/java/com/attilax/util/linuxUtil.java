package com.attilax.util;

import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import com.alibaba.fastjson.JSON;
import com.attilax.core.Consumer;
import com.attilax.core.Strutil;
import com.attilax.data.csv.csvService;
import com.attilax.util.cli.SSHHelper;
import com.attilax.util.cli.cliRet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class linuxUtil {

	public SSHHelper SSHHelper1;

	public linuxUtil(SSHHelper helper) {
	this.SSHHelper1=helper;
	}

	private cliRet setAuthChmod_rmt(String lastcmd, java.lang.String charset_console_ret,
			final List<String> ret_cmd_rzt_li) throws Exception {
		// add shell file auth

		String chAuthCmd = " chmod 777  " + lastcmd;
		ret_cmd_rzt_li.add(">>>>>>" + chAuthCmd);
		cliRet cr = SSHHelper1.sendCmd_retImdtly(chAuthCmd);
		return cr;

		// SSHHelper1.close();

	}

	public Object exeShFile_rmt_Linux_V2(java.lang.String shFile, java.lang.String charset_console_ret,
			java.lang.String rztContainId) throws Exception {
		Global.map.put("cli_finishstat_" + rztContainId, false);
		final List<String> ret_cmd_rzt_li = Lists.newArrayList();
		Global.map.put(rztContainId, ret_cmd_rzt_li);

		Map m = Maps.newConcurrentMap();
		// String update_file = upShellDir + "/update.sh";

		Object o = setAuthChmod_rmt(shFile, charset_console_ret, ret_cmd_rzt_li);

		// debuginfo.put("cmd", lastcmd);
		ret_cmd_rzt_li.add(">>>>>>" + shFile);

		final cliRet cr_sendcmd = new cliRet();
		SSHHelper1.sendCmdV2(shFile, new Consumer<String>() {

			@Override
			public void accept(String ret_line) throws Exception {
				ret_cmd_rzt_li.add(ret_line);
				cr_sendcmd.stdStr = cr_sendcmd.stdStr + "\n" + ret_line;

			}
		});

		Global.map.put("cli_finishstat_" + rztContainId, true);
		m.put("setAuthChmod_rmt_rzt", o);
		m.put("cr_sendcmd_rzt", cr_sendcmd);
		m.put("ret_cmd_rzt_li", ret_cmd_rzt_li);
		return m;
		// ret_cmd_rzt_li;

	}
	
	
	//use  sendCmd_retImdtly
	@Deprecated
	public Object exeCmd_rmt_Linux_V2(java.lang.String cmd, java.lang.String charset_console_ret,
			java.lang.String rztContainId) throws Exception {
		Global.map.put("cli_finishstat_" + rztContainId, false);
		final List<String> ret_cmd_rzt_li = Lists.newArrayList();
		Global.map.put(rztContainId, ret_cmd_rzt_li);

		Map m = Maps.newConcurrentMap();
		// String update_file = upShellDir + "/update.sh";

		 

		final cliRet cr_sendcmd =	SSHHelper1.sendCmd_retImdtly(cmd);

		Global.map.put("cli_finishstat_" + rztContainId, true);
		 
		m.put("cr_sendcmd_rzt", cr_sendcmd);
		m.put("ret_cmd_rzt_li", ret_cmd_rzt_li);
		return cr_sendcmd;
		// ret_cmd_rzt_li;

	}

	public List spaceinfo_linux(SSHHelper helper) throws Exception {

		String cmd = "df -h";
		String t = helper.getCmdRet(cmd);
		List<Map> cmdret = csvService.toTableBySpace_ParseHeader(t);
		List li = Lists.newArrayList();
		for (Map f : cmdret) {
			FileSystemView fsv = FileSystemView.getFileSystemView();
			Map m = Maps.newLinkedHashMap();
			// m.put("FileSystemView.getSystemDisplayName_driverValumeName",fsv.getSystemDisplayName(f));
			// m.put("FileSystemView.getSystemTypeDescription",fsv.getSystemTypeDescription(f));

			Object fileys = f.get("文件系统");
			if (Strutil.containsAny(fileys.toString(), "tmpfs  overlay  shm"))

				continue;
			m.put("file_getName", fileys);
			m.put("file_getPath", f.get("挂载点"));
			m.put("file_getAbsolutePath", f.get("挂载点"));

			m.put("file_getFreeSpace", getSpaceSizeGM_linux(f.get("可用")));
			m.put("file_getUsableSpace", getSpaceSizeGM_linux(f.get("可用")));
			m.put("file_getTotalSpace", getSpaceSizeGM_linux(f.get("容量")));
			m.put("used_space", getSpaceSizeGM_linux(f.get("已用")));
			li.add(m);

		}
		return li;

	}

	private Object getSpaceSizeGM_linux(Object object) {
		Map m = Maps.newLinkedHashMap();
		String s = object.toString();
		if (s.equals("0")) {
			m.put("MB", "0");
			m.put("GB", "0");
			return m;
		}
		String num_s = (String) object.toString().subSequence(0, s.length() - 1);
		Double d = Double.parseDouble(num_s);
		if (s.endsWith("G")) {
			Double mb = d * 1024d;
			m.put("MB", String.format("%.2f", mb));
			m.put("GB", String.format("%.2f", d));
		}

		if (s.endsWith("M")) {

			Double gb = d / 1024d;

			m.put("MB", String.format("%.2f", d));
			m.put("GB", String.format("%.2f", gb));

		}

		if (s.endsWith("K")) {

			Double gb = d / 1024 / 1024d;
			Double mb = d / 1024d;

			m.put("MB", String.format("%.2f", mb));
			m.put("GB", String.format("%.2f", gb));

		}

		if (s.endsWith("0")) {

			Double gb = 0d;

			m.put("MB", String.format("%.2f", d));
			m.put("GB", String.format("%.2f", gb));

		}
		return m;
	}

	public Object mkdir(String path) throws Exception {
		String cmd=" mkdir "+path;
		final cliRet cr_sendcmd =	SSHHelper1.sendCmd_retImdtly(cmd);
		return cr_sendcmd;
		
	}

}
