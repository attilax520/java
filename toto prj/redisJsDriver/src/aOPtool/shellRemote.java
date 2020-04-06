package aOPtool;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.attilax.net.URIparser;
import com.attilax.util.FileCacheManager;
import com.attilax.util.NameValuePairUtilUrlUtil;
import com.beust.jcommander.internal.Lists;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;


//  aOPtool.shellRemote
public class shellRemote {

	@Test
	public void testmain() throws Exception {
		String jumpUrl = FileUtils.readFileToString(new File("H:\\0db\\pre11.txt"));

		String txtString = FileUtils.readFileToString(new File("H:\\0db\\shell_df.txt"));
		String cmdString = jumpUrl + "?cmd=tar -cvjpf /tmp/prod5.tar /prod ";// + txtString;
		main(new String[] { cmdString });
	}

	private final static Logger logger = Logger.getLogger("aOPtool_redisExport");

	public static void main(String[] args) throws Exception {
		logger.info(JSON.toJSONString(args));

		String opcmdString = Joiner.on(" ").join(args);

		logger.info(opcmdString);

		URIparser Jmpr_urIparser = new URIparser(opcmdString);

		// 加入SCP实现远程传输文件
		Connection con = new Connection(Jmpr_urIparser.getHost(), Jmpr_urIparser.getPort());

		// 连接

		con.connect();

		// 登陆远程服务器的用户名密码
		boolean isAuthed;

		isAuthed = con.authenticateWithPassword(Jmpr_urIparser.getUser(), Jmpr_urIparser.getPwd());

		Map kv = NameValuePairUtilUrlUtil.processKeyValuePatternFromUrl(opcmdString);
		String cmd = (String) kv.get("cmd");

		logger.info(" shell cmd:" + cmd);
		Session session = con.openSession();

		session.execCommand(cmd);

		InputStream is = new StreamGobbler(session.getStdout());// 获得标准输出流
		BufferedReader brs = new BufferedReader(new InputStreamReader(is));
		for (String line = brs.readLine(); line != null; line = brs.readLine()) {
			// result.add(line);
			System.out.println(line);
		}

		// System.out.println(jsonString);

	}

}
