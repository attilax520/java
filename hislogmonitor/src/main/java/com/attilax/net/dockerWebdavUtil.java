package com.attilax.net;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.attilax.core.ForeachFunction;
import com.attilax.core.MapUtil;
import com.attilax.io.DirTraveService;
import com.attilax.io.FileUtilsAti;
import com.attilax.net.http.HttpClientUtil;
import com.attilax.text.strService;
import com.attilax.util.PathUtil;
import com.attilax.web.httpclientTest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class dockerWebdavUtil {

	// com.attilax.net.dockerWebdavUtil
	// http://192.168.1.18:1314/webdavapp/webdavurl/var/lib/docker/containers/
	// C:\logstash-6.2.4

	// C:\docker_containers C:\logstash-6.2.4
	public static void main(String[] args) throws IOException {
		
		System.out.println("aa");

		String logstashdir = "C:\\logstash-6.2.4";

		String webdavurl = "http://192.168.1.18:1314/webdavapp/webdavurl/var/lib/docker/containers/";
		webdavurl = args[0];
		logstashdir = args[1];
		List getlogList = (List) dockerWebdavUtil.getlogList(webdavurl);

		String map_strfmt = "wangjieweb:门诊前端,xiaopengweb:his中心前端，mapengweb：住院医生";
		Map m_db = MapUtil.parse(map_strfmt);

		String cfgtmp = logstashdir + File.separator + "logstash18svr_tmplt.conf";

		for (Object object : getlogList) {
			try {
				String cfgtmp_txt = FileUtilsAti.readFileToStringAutoDetectEncode(cfgtmp);
				Map m = (Map) object;
				cfgtmp_txt = cfgtmp_txt.replaceAll("@path@", PathUtil.toLinuxPathFmt(m.get("logfile").toString()));

				String dockname = m.get("dockername").toString();
				if (dockname.startsWith("/"))
					dockname = dockname.substring(1);
				Object object2 = m_db.get(dockname);
				if (object2 == null)
					continue;
				cfgtmp_txt = cfgtmp_txt.replaceAll("@dbname@", object2.toString());
				String pathname = logstashdir + File.separator + "logstash18svr_" + dockname + ".conf";
				System.out.println(" refresh logstash cfg file:" + pathname);
				FileUtils.write(new File(pathname), cfgtmp_txt);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		System.out.println("--f");

	}

	@Test
	public void showDockernames() {
		List getlogList = (List) dockerWebdavUtil
				.getlogList_all("http://192.168.1.18:1314/webdavapp/webdavurl/var/lib/docker/containers/");
		System.out.println(JSON.toJSONString(getlogList, true));

	}

	private static Object getlogList_all(String url) {
		List li = Lists.newArrayList();
		String r = new httpclientTest().httpget(url);

		Document Document1 = Jsoup.parse(r);

		Elements eles = Document1.getElementsByTag("A");

		for (Element e : eles) {

			try {
				String href = e.attr("href"); // Element.attr("href");
				String id = e.text();
				if (!id.endsWith("/"))
					continue;
				id = id.substring(0, id.length() - 1);
				// System.out.println(href);

				if (href.trim().length() <= 0)
					continue;

				String dockername = get_dockername(url, id);

				long httpfileSize1 = getLogsize(url, id);

				Map m = Maps.newConcurrentMap();
				m.put("dockername", dockername);
				m.put("size", httpfileSize1);
				m.put("fid", id);
				m.put("logfile_http", get_logfile(url, id));
				m.put("logfile", get_logfile("/var/lib/docker/containers/", id));
				// System.out.println(m);
				li.add(m);

			} catch (Exception e2) {
				e2.printStackTrace();
			}

			// JSONObject

		}
		return li;
	}

	private static Object getlogList(String url) throws IOException {

		if (url.startsWith("http"))
			return getlogList_webdav(url);
		else
			return getlogList_loc(url);

	}

	@Test
	public void test_getlogList_loc() throws IOException {
		String url = "C:\\docker_containers";
		System.out.println(JSON.toJSONString(getlogList_loc(url), true));
	}

	private static Object getlogList_loc(String url) throws IOException {
		List li = Lists.newArrayList();

		File contain_root = new File(url);
		File[] contains = contain_root.listFiles();
		for (File file : contains) {
			String id = file.getName();
			String dockername = get_dockername_loc(url, id);
			if (strService.contain(dockername, "/redis1,/activemq,/zookeeper"))
				continue;
			long httpfileSize1 = getLogsize_loc(url, id);

			if (httpfileSize1 < 10 * 1000)
				continue;
			Map m = Maps.newConcurrentMap();
			dockername = dockername.substring(1);
			m.put("dockername", dockername);
			m.put("size", httpfileSize1);
			m.put("fid", id);
			m.put("logfile_http", get_logfile(url, id));
			m.put("logfile", get_logfile(url, id));
			// System.out.println(m);
			li.add(m);
		}

		return li;
	}

	private static String get_dockername_loc(String url, String id) throws IOException {
		String jsonf = url + "/@id@/config.v2.json";
		jsonf = jsonf.replaceAll("@id@", id);
		String txt = FileUtils.readFileToString(new File(jsonf));
		JSONObject jo = JSONObject.parseObject(txt);

		return jo.getString("Name");
	}

	private static long getLogsize_loc(String url, String id) {
		String jsonf = url + "/@id@/@id@-json.log";
		jsonf = jsonf.replaceAll("@id@", id);
		File f = new File(jsonf);
		return f.length();
	}

	private static Object getlogList_webdav(String url) {
		List li = Lists.newArrayList();
		String r = new httpclientTest().httpget(url);

		Document Document1 = Jsoup.parse(r);

		Elements eles = Document1.getElementsByTag("A");

		for (Element e : eles) {

			try {
				String href = e.attr("href"); // Element.attr("href");
				String id = e.text();
				if (!id.endsWith("/"))
					continue;
				id = id.substring(0, id.length() - 1);
				// System.out.println(href);

				if (href.trim().length() <= 0)
					continue;

				String dockername = get_dockername(url, id);
				if (strService.contain(dockername, "/redis1,/activemq,/zookeeper"))
					continue;
				long httpfileSize1 = getLogsize(url, id);

				if (httpfileSize1 < 10 * 1000)
					continue;
				Map m = Maps.newConcurrentMap();
				dockername = dockername.substring(1);
				m.put("dockername", dockername);
				m.put("size", httpfileSize1);
				m.put("fid", id);
				m.put("logfile_http", get_logfile(url, id));
				m.put("logfile", get_logfile("/var/lib/docker/containers/", id));
				// System.out.println(m);
				li.add(m);

			} catch (Exception e2) {
				e2.printStackTrace();
			}

			// JSONObject

		}
		return li;
	}

	private static Object get_logfile(String url, String id) {
		String jsonf = url + "/@id@/@id@-json.log";
		jsonf = jsonf.replaceAll("@id@", id);
		return jsonf;
	}

	private static String get_dockername(String url, String id) {
		String jsonf = "http://192.168.1.18:1314";
		jsonf = url + "/@id@/config.v2.json";
		jsonf = jsonf.replaceAll("@id@", id);
		String txt = new HttpClientUtil().httpget(jsonf);
		JSONObject jo = JSONObject.parseObject(txt);

		return jo.getString("Name");
	}

	private static long getLogsize(String url, String id) {
		String jsonf = "http://192.168.1.18:1314@id@/config.v2.json";
		jsonf = url + "/@id@/@id@-json.log";
		jsonf = jsonf.replaceAll("@id@", id);
		// System.out.println(jsonf);
		// ;
		return new HttpClientUtil().httpfileSize(jsonf);
	}

}
