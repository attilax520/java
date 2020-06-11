package com.attilax.web.es;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.attilax.Charset.EncodingDetect;
import com.attilax.collection.mapBuilder;
import com.attilax.web.HttpClientUtil;
import com.cnhis.cloudhealth.module.log.Cls1;

public class esUtil {

	public static void main(String[] args) throws IOException {
		// addData();
		logger.info("--main");
		// addData_post(); -d "{\"query\":{\"match\":{\"desc\":\"管理\"}}}"")
		Map m = new HashMap() {
			{

				this.put("query", new HashMap() {
					{
						this.put("match", new HashMap() {
							{
								this.put("txt", "webdav");
							}
						});
						
						this.put("match", new HashMap() {
							{
								this.put("txt", "编码");
							}
						});
					}
				});

			}
		};
		Map m2 = new HashMap() {{
			this.put("bool", new HashMap() {
							{
								this.put("must", new ArrayList(){{
									this.add(mapBuilder.$().put("match", mapBuilder.$().put("txt", "webdav").build() ).build());
									this.add(mapBuilder.$().put("match", mapBuilder.$().put("txt", "编码").build() ).build());
								}});
							}
						});
			
		}};
	//	System.out.println(query_get("http://localhost:9200/accounts/person/_search", m));   //
 	//System.out.println(query("http://localhost:9200/accounts/person/_search", m));
	//	String r = query("http://localhost:9200/index_art/type1/_search", m);
		String r = queryV2("http://localhost:9200/index_art/type1/_search", m2);
		JSONObject jo=JSONObject.parseObject(r);
		logger.info(JSON.toJSONString(jo, true));
		System.out.println(JSON.toJSONString(jo, true));
	//	System.out.println(query);

	}
	static final Logger logger = Logger.getLogger(Cls1.class);
	private static String queryV2(String url, final Map m2) {
		Map m = new HashMap() {
			{

				this.put("query", m2);

			}
		};
		return query(url, m);
	}

	public static void addData() throws IOException {
		String f = "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\web\\es\\singledoc.txt";
		String code = EncodingDetect.getJavaEncode(f);
		String t = FileUtils.readFileToString(new File(f), code);
		Map map = JSON.parseObject(t);

		String r = new HttpClientUtil().httpput("http://localhost:9200/indexmy1/type1/2", t, "utf8");
		JSONObject jo=JSONObject.parseObject(r);
		System.out.println(JSON.toJSONString(jo, true));
	}

	// "C:\Program Files\Git\mingw64\bin\curl.exe" -H
	// "Content-Type:application/json" -X POST
	// "http://localhost:9200/accounts/person" --data
	// @C:\Users\attilax\Desktop\datajson.txt
	public static void addData_post() throws IOException {
		String f = "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\web\\es\\singledoc.txt";
		String code = EncodingDetect.getJavaEncode(f);
		String t = FileUtils.readFileToString(new File(f), code);

		// Map map=JSON.parseObject(t);

		String url = "http://localhost:9200/indexmy1/type1";
		String r = new HttpClientUtil().httppost(url, t, "utf8");
		System.out.println(r);
	}

	public static String query(String url, Map m)  {
		String f = "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\web\\es\\singledoc.txt";
		String code = EncodingDetect.getJavaEncode(f);
		String t = JSON.toJSONString(m);

		// Map map=JSON.parseObject(t);

		String r = new HttpClientUtil().httppost(url, t, "utf8");
		return r;
	}
	
	
//	public static String query_get(String url, Map m) throws IOException {
//		String f = "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\web\\es\\singledoc.txt";
//		String code = EncodingDetect.getJavaEncode(f);
//		String t = JSON.toJSONString(m);
//t=URLEncoder.encode(t);
//		// Map map=JSON.parseObject(t);
//		url =url +"?"+t;
//		System.out.println(url);
//		//String r = new HttpClientUtil().httpget(url );   //400err
//		String r = new HttpClientUtil().httpget_wzbody(url,t );   //400err
//		
//		return r;
//	}

}
