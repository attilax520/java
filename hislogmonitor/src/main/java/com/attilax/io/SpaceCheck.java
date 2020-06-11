package com.attilax.io;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import com.alibaba.fastjson.JSON;
import com.attilax.collection.mapBuilder;
import com.attilax.core.Strutil;
import com.attilax.data.csv.csvService;
import com.attilax.util.cli.SSHHelper;
import com.attilax.util.cli.cliRet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

//com.attilax.io.SpaceCheck
public class SpaceCheck {
	public static void main(String[] args) throws Exception {

		List li = spaceinfo();

		String linux_password = "cloudhealth";
		Map m2 = mapBuilder.$().put("os", "linux").put("host", "192.168.1.18").put("user", "root")
				.put("pwd", linux_password).build();
		SSHHelper helper = new SSHHelper(m2.get("host").toString(), 22, m2.get("user").toString(),
				m2.get("pwd").toString());

		li = new SpaceCheck().spaceinfo_linux(helper);

		li = Lists.newArrayList();
		li.add("192.168.1.18,root,cloudhealth");
		li.add("localhost,root,cloudhealth");
		System.out.println(JSON.toJSONString(li, true));

	}

	public List spaceinfo(String host, String user, String pwd, String os) throws Exception {
		if (host.equals("127.0.0.1") || host.equals("localhost")) {

			return spaceinfo();
		}
		Map m2 = mapBuilder.$().put("os", "linux").put("host", host).put("user", user).put("pwd", pwd).build();
		SSHHelper helper = new SSHHelper(m2.get("host").toString(), 22, m2.get("user").toString(),
				m2.get("pwd").toString());

		return new SpaceCheck().spaceinfo_linux(helper);
	}

	public List spaceinfo(String host, String user, String pwd) throws Exception {
		if (host.equals("127.0.0.1") || host.equals("localhost")) {

			return spaceinfo();
		}
		Map m2 = mapBuilder.$().put("os", "linux").put("host", host).put("user", user).put("pwd", pwd).build();
		SSHHelper helper = new SSHHelper(m2.get("host").toString(), 22, m2.get("user").toString(),
				m2.get("pwd").toString());

		return new SpaceCheck().spaceinfo_linux(helper);
	}

	public List spaceinfo_linux(String host, String user, String pwd) throws Exception {
		// Object pwd;
		// Object user;
		Map m2 = mapBuilder.$().put("os", "linux").put("host", host).put("user", user).put("pwd", pwd).build();
		SSHHelper helper = new SSHHelper(m2.get("host").toString(), 22, m2.get("user").toString(),
				m2.get("pwd").toString());

		return new SpaceCheck().spaceinfo_linux(helper);
	}

	@SuppressWarnings("all")
	public List spaceinfo_linux(SSHHelper helper) throws Exception {

		String cmd = "df -h";
		cliRet sendCmd_retImdtly_retinfo = helper.sendCmd_retImdtly(cmd);
		String t = sendCmd_retImdtly_retinfo.stdStr;

		List<Map> cmdret = csvService.toTableBySpace_ParseHeader(t);
		List li = Lists.newArrayList();
		for (Map m_rec : cmdret) {
			FileSystemView fsv = FileSystemView.getFileSystemView();
			Map m = Maps.newLinkedHashMap();
			// m.put("FileSystemView.getSystemDisplayName_driverValumeName",fsv.getSystemDisplayName(f));
			// m.put("FileSystemView.getSystemTypeDescription",fsv.getSystemTypeDescription(f));
			Map f = addCnField(m_rec);
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

	private Map addCnField(Map m_rec) {
		if (m_rec.get("Filesystem") != null)
			m_rec.put("文件系统", m_rec.get("Filesystem"));
		if (m_rec.get("Size") != null)
			m_rec.put("容量", m_rec.get("Size"));
		if (m_rec.get("Used") != null)
			m_rec.put("已用", m_rec.get("Used"));
		if (m_rec.get("Avail") != null)
			m_rec.put("可用", m_rec.get("Avail"));
		if (m_rec.get("Mounted") != null)
			m_rec.put("挂载点", m_rec.get("Mounted"));

		return m_rec;
	}

	private Map addEngField(Map m_rec) {
		if (m_rec.get("文件系统") != null)
			m_rec.put("Filesystem", m_rec.get("文件系统"));
		if (m_rec.get("文件系统") != null)
			m_rec.put("Filesystem", m_rec.get("文件系统"));
		if (m_rec.get("文件系统") != null)
			m_rec.put("Filesystem", m_rec.get("文件系统"));
		return null;
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

	/**
	 * 
	 * @return
	 */
	public static List spaceinfo() {
		File[] roots = File.listRoots();
		List li = Lists.newArrayList();
		for (File f : roots) {
			FileSystemView fsv = FileSystemView.getFileSystemView();
			Map m = Maps.newLinkedHashMap();
			m.put("FileSystemView.getSystemDisplayName_driverValumeName", fsv.getSystemDisplayName(f));
			m.put("FileSystemView.getSystemTypeDescription", fsv.getSystemTypeDescription(f));

			m.put("file_getName", f.getName());
			m.put("file_getPath", f.getPath());
			m.put("file_getAbsolutePath", f.getAbsolutePath());

			m.put("file_getFreeSpace", getSpaceSizeGM(f.getFreeSpace()));
			m.put("file_getUsableSpace", getSpaceSizeGM(f.getUsableSpace()));
			m.put("file_getTotalSpace", getSpaceSizeGM(f.getTotalSpace()));
			m.put("used_space", getSpaceSizeGM(f.getTotalSpace() - f.getFreeSpace()));
			li.add(m);

			// System.out.println(f.getPath());
			// System.out.println(file_getName());
			// System.out.println("Free space = " +
			// (f.getFreeSpace()/(1024*1024))/1024+"G"); //显示GB大小
			// System.out.println("Usable space = " + f.getUsableSpace());
			// System.out.println("Total space = " + f.getTotalSpace());
			// System.out.println("used space = " +
			// (f.getTotalSpace()-f.getFreeSpace()));
			// System.out.println();
		}
		return li;
	}

	private static Object getSpaceSizeGM(long freeSpace) {
		Map m = Maps.newLinkedHashMap();
		double value = (double) freeSpace / (double) (1024 * 1024 * 1024);
		m.put("GB", String.format("%.2f", value));
		double value2 = (double) freeSpace / (double) (1024 * 1024);
		m.put("MB", String.format("%.2f", value2));
		return m;
	}
}