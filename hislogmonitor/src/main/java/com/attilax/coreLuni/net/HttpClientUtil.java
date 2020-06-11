/**
 * Copyright(C), 2013-2014
 *		 Shenzhen Coordinates Software Development Co., Ltd.
 * com.cnhis.cloudhealth.open.service.plugin.weixin.util.HttpRequest.java
 */
package com.attilax.coreLuni.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
/**
 * @Description: TODO(类功能描述)
 * @Package com.cnhis.cloudhealth.open.service.plugin.weixin.util
 * @ClassName HttpRequest
 * @author  Administrator
 * @date 2016年6月23日 下午3:31:03 
 * @version 版本号 V1.0.0
 */

public class HttpClientUtil {
	
	
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
	/**
     * 保存消息发送记录
     * @param restUrl
     * @param homessageMap
     * @return 如果保存成功，返回对象的json格式内容；保存失败，返回空字符串
     * */
    public static String postEntiry2RestEasy(String restUrl,Map<String,Object> homessageMap){
    	
    	if(restUrl ==null || homessageMap==null || homessageMap.isEmpty()){
    		throw new IllegalArgumentException("invalid parameter to save HomessageRecord ! ");
    	}
    	
    	StringBuilder outputBuider = new StringBuilder();
    	
    	try {
    		
    		DefaultHttpClient httpClient = new DefaultHttpClient();
    		HttpPost postRequest = new HttpPost(restUrl);

    		JSONObject paramsEntity =new JSONObject(homessageMap);
    		StringEntity input = new StringEntity(paramsEntity.toString(),"UTF-8");
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
        Map<String, Object> recordParams = new HashMap<String,Object>();
    	//key参考HoMessageRecord参数名 
        recordParams.put("receiverName", "雍正爷");
    	recordParams.put("description", "雍正爷很勤劳");

    	String local = "http://localhost:8080/crmweb/cloud/hoMessageRecord";
    	String remote ="http://120.25.59.85:10500/crmweb/cloud/hoMessageRecord";
    	String rtnObj = HttpClientUtil.postEntiry2RestEasy(remote, recordParams);
    	System.out.println(rtnObj);
    	}
}
