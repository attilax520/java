package com.attilax.web.es;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.alibaba.fastjson.JSON;
import com.attilax.Charset.EncodingDetect;
import com.attilax.core.ForeachFunction;
import com.attilax.fulltxt.fulltxtServ;
import com.attilax.io.DirTraveService;
import com.attilax.web.HttpClientUtil;
import com.cnhis.cloudhealth.module.log.Cls1;

public class esUtil_docx2 {

	public static void main(String[] args) throws IOException {
		// addData();

		// addData_post(); -d "{\"query\":{\"match\":{\"desc\":\"管理\"}}}"")
		Map m = new HashMap() {
			{

				this.put("query", new HashMap() {
					{
						this.put("match", new HashMap() {
							{
								this.put("desc", "管理");
							}
						});
					}
				});

			}
		};

		// System.out.println(query_get("http://localhost:9200/accounts/person/_search",
		// m)); //
		// System.out.println(query("http://localhost:9200/accounts/person/_search",
		// m));

		addData();
		System.out.println("--");

	}

	static final Logger logger = Logger.getLogger(esUtil_docx2.class);
	private static final String HOST = "localhost";
	private static final int PORT = 9200;
	
	public static void addData() throws IOException {
		final ThreadPoolExecutor ExecutorService1_theardpool = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
		String dir = "C:\\Users\\attilax\\Documents\\sum doc all txtver  v2 raf ext notbek";
		final String url = "http://localhost:9200/index_art/type1";

		// 创建客户端
	//	TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(HOST), PORT);
//		java.util.funcion
//		@SuppressWarnings("resource")
//		final TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(transportAddress);

	//	logger.debug("Elasticsearch connect info:" + client.toString());

		new DirTraveService().traveV5_vS522(new File(dir), new ForeachFunction() {

			@Override
			public void each(int count, final File f) throws Exception {
				String code = EncodingDetect.getJavaEncode(f.getAbsolutePath());
				final String t = FileUtils.readFileToString(f, code);
				logger.info("cn:" + count + f.getAbsolutePath());
				// kw="成年 后世";
				Map m = new HashMap() {
					{

						this.put("f", f.getName());
						this.put("txt", t);
						this.put("f_fullpath", f.getAbsolutePath());

					}
				};
				final String addParams_json = JSON.toJSONString(m);

//				IndexResponse response = client.prepareIndex("index_db_db1", "type_table_atisumdoc")
//						.setSource(addParams_json, XContentType.JSON).get();
//				logger.info(JSON.toJSONString(response));
				FutureTask ft=new FutureTask (new Callable () {

					@Override
					public Object call() throws Exception {
						  String r = new HttpClientUtil().httppost(url, addParams_json, "utf8");
							logger.info(r);
						return null;
					}
				});
				ExecutorService1_theardpool.submit(ft);
				
			

			}

		});

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

	public static String query(String url, Map m) throws IOException {
		String f = "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\web\\es\\singledoc.txt";
		String code = EncodingDetect.getJavaEncode(f);
		String t = JSON.toJSONString(m);

		// Map map=JSON.parseObject(t);

		String r = new HttpClientUtil().httppost(url, t, "utf8");
		return r;
	}

	// public static String query_get(String url, Map m) throws IOException {
	// String f =
	// "C:\\0wkspc\\hislog\\src\\main\\java\\com\\attilax\\web\\es\\singledoc.txt";
	// String code = EncodingDetect.getJavaEncode(f);
	// String t = JSON.toJSONString(m);
	// t=URLEncoder.encode(t);
	// // Map map=JSON.parseObject(t);
	// url =url +"?"+t;
	// System.out.println(url);
	// //String r = new HttpClientUtil().httpget(url ); //400err
	// String r = new HttpClientUtil().httpget_wzbody(url,t ); //400err
	//
	// return r;
	// }

}
