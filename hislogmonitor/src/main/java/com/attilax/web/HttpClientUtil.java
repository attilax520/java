/**
 * Copyright(C), 2013-2014
 *		 Shenzhen Coordinates Software Development Co., Ltd.
 * com.cnhis.cloudhealth.open.service.plugin.weixin.util.HttpRequest.java
 */
package com.attilax.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.attilax.util.ExUtil;

/**
 * 
 * v3 s521 add post put ati
 * 
 * @Description: TODO(类功能描述)
 * @Package com.cnhis.cloudhealth.open.service.plugin.weixin.util
 * @ClassName HttpRequest
 * @author Administrator
 * @date 2016年6月23日 下午3:31:03
 * @version 版本号 V1.0.0
 */

@Deprecated
public class HttpClientUtil {

	
	
	/**
	 * only params not name pair
	 * @param url
	 * @param param
	 * @param responseParseEncode
	 * @return
	 */
	public String httppost(String url, String param, String responseParseEncode) {

		
		//HttpEntity
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		HttpPost HttpPut1 = new HttpPost(url);
		
		 

		// 构建消息实体
		StringEntity entity = new StringEntity(param, Charset.forName("UTF-8"));
		entity.setContentEncoding("UTF-8");
		// 发送Json格式的数据请求
		entity.setContentType("application/json");

		HttpPut1.setEntity(entity);
		// httpCilent.execute(httpGet);
		byte[] arr = execute(httpCilent, HttpPut1);

		try {
			return new String(arr, responseParseEncode);
		} catch (UnsupportedEncodingException e) {
			ExUtil.throwExV2(e);
		}
		ExUtil.throwEx("ex");
		return responseParseEncode;
	}
	
	/**
	 * only params not name pair
	 * @param url
	 * @param param
	 * @param responseParseEncode
	 * @return
	 */
	public String httpput(String url, String param, String responseParseEncode) {

		
		//HttpEntity
		CloseableHttpClient httpCilent = HttpClients.createDefault();
		HttpPut HttpPut1 = new HttpPut(url);
		
		 

		// 构建消息实体
		StringEntity entity = new StringEntity(param, Charset.forName("UTF-8"));
		entity.setContentEncoding("UTF-8");
		// 发送Json格式的数据请求
		entity.setContentType("application/json");

		HttpPut1.setEntity(entity);
		// httpCilent.execute(httpGet);
		byte[] arr = execute(httpCilent, HttpPut1);

		try {
			return new String(arr, responseParseEncode);
		} catch (UnsupportedEncodingException e) {
			ExUtil.throwExV2(e);
		}
		ExUtil.throwEx("ex");
		return responseParseEncode;
	}

	public String httpput(String url, Map map, String responseParseEncode) {
		byte[] arr = httpput(url, map);
		try {
			return new String(arr, responseParseEncode);
		} catch (UnsupportedEncodingException e) {
			ExUtil.throwExV2(e);
		}
		ExUtil.throwEx("ex");
		return responseParseEncode;
	}

	/**
	 * ati
	 * 
	 * @param url
	 * @return
	 */
	public byte[] httpput(String url, Map map) {
		CloseableHttpClient httpCilent = HttpClients.createDefault();// Creates
																		// CloseableHttpClient
																		// instance
																		// with
																		// default
																		// configuration.
		HttpPut HttpPut1 = new HttpPut(url);

		HttpEntity urlEncodedFormEntity = null;
		try {
			urlEncodedFormEntity = asList_NameValuePair(map);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpPut1.setEntity(urlEncodedFormEntity);
		// httpCilent.execute(httpGet);
		return execute(httpCilent, HttpPut1);
	}

	private UrlEncodedFormEntity asList_NameValuePair(Map map) throws UnsupportedEncodingException {
		List<NameValuePair> values_ListNameValuePair = new ArrayList<NameValuePair>();

		// values.add(new BasicNameValuePair("id", "1"));

		// values.add(new BasicNameValuePair("name", "xiaohong"));
		addPostParams(map, values_ListNameValuePair);
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(values_ListNameValuePair, HTTP.UTF_8);
		return urlEncodedFormEntity;
	}

	private byte[] execute(CloseableHttpClient httpCilent, HttpUriRequest HttpUriRequest1) {
		byte[] srtResult;

		HttpResponse httpResponse;
		try {
			httpResponse = httpCilent.execute(HttpUriRequest1);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 200 || statusCode == 201) {

				HttpEntity entity = httpResponse.getEntity();

				srtResult = EntityUtils.toByteArray(entity);

				return srtResult;
			} else if (statusCode == 400) {
				throw new RuntimeException("httpResponse.getStatusLine().getStatusCode() == 400");
			} else if (statusCode == 500) {
				throw new RuntimeException("httpResponse.getStatusLine().getStatusCode() == 500");
			}else
				throw new RuntimeException("httpResponse.getStatusLine().getStatusCode() == "+statusCode);

		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} // 获得返回的结果
	
	}

	public String doPost(String url, Map<String, String> map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			addPostParams(map, list);
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	private void addPostParams(Map<String, String> map, List<NameValuePair> list) {
		Iterator iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			 
			Entry<String, Object> elem = (Entry<String, Object>) iterator.next();
			list.add(new BasicNameValuePair(elem.getKey(), elem.getValue().toString()));
		}
		
		
		
		 
	}

	public String httpget(String url) {
		CloseableHttpClient httpCilent = HttpClients.createDefault();// Creates
																		// CloseableHttpClient
																		// instance
																		// with
																		// default
																		// configuration.
		HttpGet httpGet = new HttpGet(url);

		// httpCilent.execute(httpGet);
		String srtResult = "";

		HttpResponse httpResponse;
		try {
			httpResponse = httpCilent.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				srtResult = EntityUtils.toString(httpResponse.getEntity());

				return srtResult;
			} else if (httpResponse.getStatusLine().getStatusCode() == 400) {
				throw new RuntimeException("httpResponse.getStatusLine().getStatusCode() == 400");
			} else if (httpResponse.getStatusLine().getStatusCode() == 500) {
				throw new RuntimeException("httpResponse.getStatusLine().getStatusCode() == 500");
			}

		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} // 获得返回的结果
		throw new RuntimeException(" err");
	}
	
	
	public String httpget_wzbody(String url,String bodystr) {
		CloseableHttpClient httpCilent = HttpClients.createDefault();// Creates
																		// CloseableHttpClient
																		// instance
																		// with
																		// default
																		// configuration.
		HttpGet httpGet = new HttpGet(url);
		
		
		// 构建消息实体
		StringEntity entity = new StringEntity(bodystr, Charset.forName("UTF-8"));
		entity.setContentEncoding("UTF-8");
		// 发送Json格式的数据请求
		entity.setContentType("application/json");

		((HttpResponse) httpGet).setEntity(entity);
		
		

		// httpCilent.execute(httpGet);
		String srtResult = "";

		HttpResponse httpResponse;
		try {
			httpResponse = httpCilent.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {

				srtResult = EntityUtils.toString(httpResponse.getEntity());

				return srtResult;
			} else if (httpResponse.getStatusLine().getStatusCode() == 400) {
				throw new RuntimeException("httpResponse.getStatusLine().getStatusCode() == 400");
			} else if (httpResponse.getStatusLine().getStatusCode() == 500) {
				throw new RuntimeException("httpResponse.getStatusLine().getStatusCode() == 500");
			}

		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		} // 获得返回的结果
		throw new RuntimeException(" err");
	}


	/**
	 * 保存消息发送记录
	 * 
	 * @param restUrl
	 * @param homessageMap
	 * @return 如果保存成功，返回对象的json格式内容；保存失败，返回空字符串
	 */
	public static String postEntiry2RestEasy(String restUrl, Map<String, Object> homessageMap) {

		if (restUrl == null || homessageMap == null || homessageMap.isEmpty()) {
			throw new IllegalArgumentException("invalid parameter to save HomessageRecord ! ");
		}

		StringBuilder outputBuider = new StringBuilder();

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(restUrl);

			JSONObject paramsEntity = new JSONObject(homessageMap);
			StringEntity input = new StringEntity(paramsEntity.toString(), "UTF-8");
			input.setContentType("application/json;");
			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() == 201) {
				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				String outputLine = null;

				while ((outputLine = br.readLine()) != null) {
					outputBuider.append(outputLine);
				}
				httpClient.getConnectionManager().shutdown();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputBuider.toString();
	}

	public static void main(String[] args) {
		Map<String, Object> recordParams = new HashMap<String, Object>();
		// key参考HoMessageRecord参数名
		recordParams.put("receiverName", "雍正爷");
		recordParams.put("description", "雍正爷很勤劳");

		String local = "http://localhost:8080/crmweb/cloud/hoMessageRecord";
		String remote = "http://120.25.59.85:10500/crmweb/cloud/hoMessageRecord";
		String rtnObj = HttpClientUtil.postEntiry2RestEasy(remote, recordParams);
		System.out.println(rtnObj);
	}
}
