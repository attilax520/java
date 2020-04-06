package redisJsDriverPkg;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.attilax.util.FileUtilsV2t33;
import com.google.common.collect.Maps;

import redis.clients.jedis.Jedis;

@SuppressWarnings("all")
public class RedisUtil {

	public static void main(String[] args) throws Exception {

	  
		Object v = parse(string);
		System.out.println(v);
		System.out.println(JSON.toJSONString(v, true));

		 
		// -get access_token";
//		args =	StringUtils.splitPreserveAllTokens(string);  //multi whiteblank is pslite to mulit ,want to be one token
//		args =	StringUtils.splitPreserveAllTokens(string, ' ') ;
//		args =	StringUtils.splitPreserveAllTokens(string, " ") ;

		// args = StringUtils.splitByWholeSeparatorPreserveAllTokens( string, " ") ;

		// args = StringUtils.splitByCharacterType(string);
//		CommandLineParser parser = new BasicParser();
//		System.out.println( parse(string));
		// 往list 里面存值
		// jedis.rpush("listkey1", "内容1", "内容2");
		// 从list获取值
		// System.out.println(jedis.lindex("listkey1", 0));

		// hash类型的设置与读取
//	        Map m=Maps.newConcurrentMap();
//	        m.put("mykey1", "myval1");
//	        jedis.hmset("hskey", m);
//	        Map m2=     jedis.hgetAll("hashkey1");
//	        System.out.println(m2);

	}

	public static Object parse(String cmdString) throws Exception {
//		if (FileUtilsV2t33.isPath(cmdString)) {
//			if (!new File(cmdString).exists())
//				throw new FileNotFoundException(cmdString);
//			cmdString = FileUtils.readFileToString(new File(cmdString)).trim();
//
//		}

		return parse(StringUtils.splitByWholeSeparator(cmdString, " "));

	}

	public static Object parse(String[] args) throws Exception {
		System.out.println(JSON.toJSON(args));
	 	// access_token
		final Options options = new Options();
		final Option option_get = new Option("url", true, "Configuration url");
		// 第三个参数为true表示需要额外参数：
		final Option option = new Option("port", true, "cfg port");
		options.addOption(option);
		options.addOption(option_get);
		options.addOption(new Option("pwd", true, "cfg pwd"));
		options.addOption(new Option("db", true, "cfg pwd"));
		options.addOption(new Option("get", true, "cfg get"));
		options.addOption(new Option("smembers", true, "cfg smembers"));
		options.addOption(new Option("cfg", true, "cfgcfg  "));
		CommandLineParser parser = new DefaultParser();

		CommandLine cmdline = parser.parse(options, args);
		if (cmdline.getOptionValue("cfg") != null) {
			String cmd_file = FileUtils.readFileToString(new File(cmdline.getOptionValue("cfg"))).trim();
			String[] a = StringUtils.splitByWholeSeparator(cmd_file, " ");
			// String[] a_new=new String[a.length+args.length];

			args=	ArrayUtils.addAll(args, a);
			cmdline = parser.parse(options, args);
		}

		// 连接redis服务器
		String url_s = cmdline.getOptionValue("url");
		URL url = new URL (url_s);
		System.out.println(url);
	//	System.out.println(JSON.toJSON(url));
		Jedis jedis = new Jedis(url.getHost(),

				url.getPort());
		// jedis.
		jedis.auth(url.getUserInfo().split(":")[1]);
		jedis.select(Integer.parseInt( url.getPath().substring(1)) - 1); // select db

		// 查看服务是否运行
		System.out.println("服务正在运行: " + jedis.ping());

		// 设置kv 值,添加数据
		jedis.set("key1", "value1");

		// hash类型的设置与读取
		Map m = Maps.newConcurrentMap();
		m.put("mykey1", "myval1");
		jedis.hmset("hskey", m);

		// set data
		jedis.sadd("setdtKey1", "m1", "v2");
		Set<String> smembers = jedis.smembers("setdtKey1");

		// 获取值

		if (cmdline.hasOption("get"))
			return jedis.get(cmdline.getOptionValue("get"));
		if (cmdline.hasOption("smembers")) // get set data
			return jedis.smembers(cmdline.getOptionValue("smembers"));

		return null;

	}

}
