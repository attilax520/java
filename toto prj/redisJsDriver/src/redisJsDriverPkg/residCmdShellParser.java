package redisJsDriverPkg;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.attilax.data.RedisUtilV2t33;
import com.attilax.util.NameValuePairUtilUrlUtil;
import com.google.common.base.Joiner;

import redis.clients.jedis.Jedis;

//  redisJsDriverPkg.residCmdShellParser
@SuppressWarnings("all")
public class residCmdShellParser {
	private final static Logger logger = Logger.getLogger("aOPtool_redisExport");
	@Test
	public void testmain() throws Exception {
		String string = "http://{0}@r-uf6o2jdhb4bqojtwro.redis.rds.aliyuncs.com:6379/2?cmd=get access_token";
		String urltxt = FileUtils.readFileToString(new File("h:/0db/redis_aliyun_userinfo.txt"));

			string=MessageFormat.format(string, urltxt);
			logger.info(string);
			main(new String[] {  string });
			
			
//			String string = "http://{0}@r-uf6o2jdhb4bqojtwro.redis.rds.aliyuncs.com:6379/2?cmd=get access_token";
//			String urltxt = FileUtils.readFileToString(new File("h:/0db/redis_36url.txt"));
//	 
//	String mainArg	 = urltxt + "?cmd=get aaa";
//	mainArg = URLEncoder.encode(mainArg);
//		main(new String[] {  urltxt + "?cmd=get aaa" });
	}

	public static void main(String[] args) throws URISyntaxException {
		logger.info(JSON.toJSONString(args) );
		String urlteString = "http://111.15.12.32/path?query=15";
		URI uri = new URI(urlteString);
		String opcmdString = Joiner.on(" ").join(args);
				//args[0];
		opcmdString = URLDecoder.decode(opcmdString);
		logger.info(opcmdString);
		Jedis jedis = RedisUtilV2t33.getJedisObj(opcmdString);
//	URI url = new URI(opcmdString);
//String queryString=	url.getQuery();
		Map kv = NameValuePairUtilUrlUtil.processKeyValuePatternFromUrl(opcmdString);
		Object rdisCmd = kv.get("cmd");
		
		logger.info("rdisCmd:"+rdisCmd);
		System.out.println(rdisCmd);
		String[] tokin_arrStrings=StringUtils.splitByWholeSeparator(rdisCmd.toString(), " ");
        String op=tokin_arrStrings[0];
        if(op.equals("get"))
        {
        	String keyString=tokin_arrStrings[1];
        	String rzt = jedis.get(keyString);
			System.out.println(rzt); ;
        	logger.info("rdisCmd rzt:"+rzt);
        	
        }
	}

}
