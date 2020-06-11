package com.cnhis.cloudhealth.module.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;











import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnhis.cloudhealth.common.utils.StringUtils;
import com.cnhis.cloudhealth.module.license.vo.LicenseJSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings("all")
public class LicenseApi {
	
	private static Logger logger = LoggerFactory.getLogger(LicenseApi.class);
	
	public static String getDogId(String ip){
		 Map<String, String> params = new HashMap<String, String>();
		 String url="http://"+ip+":8888/list.license";
		 logger.info("当前请求URL地址为："+url);
	     String result =  httpPost(url, params);
		try {
			// 将字符串转为XML
			if(!StringUtils.isEmpty(result)){
				Document doc = DocumentHelper.parseText(result);
				Element rootElt = doc.getRootElement(); // 获取根节点
				Iterator iter = rootElt.elementIterator("RegInfo");
			    while(iter.hasNext()){
		        	Element element = (Element) iter.next(); // 获取标签对象
		        	Iterator host = element.elementIterator("Host");
		        	while(host.hasNext()){
		        		Node node = (Node)host.next();
		        		String asXML = node.asXML();
		        		Map<String, String> map = asXML(asXML);
		        		if(map != null){
		        			System.out.println(map.get("Name"));
		        			return map.get("Name");
		        		}
		        	}
			    }
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Title: asXML   
	 * @Description: 授权产品字符串处理
	 * @param: @param str
	 * @param: @return
	 * @return: Map<String,String>      
	 * @throws
	 */
	public static Map<String,String> asXML(String str) {
		Map<String,String> map = new HashMap<String,String>();
		String [] arr = str.split("\\s");
		for (int i = 1; i < arr.length; i++) {
			String stra=arr[i];
			String [] newStr = stra.split("=");
			String key = "";
			String value = "";
			for (int j = 0; j < newStr.length; j++) {
				if(j == 1){
					value = newStr[j];
					value = value.substring(value.indexOf("\"")+1,value.lastIndexOf("\""));
				}else{
					key = newStr[j];
				}
			}
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * @Title: httpPost   
	 * @Description: http请求工具类
	 * @param: @param url
	 * @param: @param params
	 * @param: @return
	 * @return: String      
	 * @throws
	 */
    public static String httpPost(String url, Map<String, String> params){
		try {
			HttpPost httpPost = new HttpPost(url);
            HttpClient client = new DefaultHttpClient();
            List<NameValuePair> valuePairs = new ArrayList<NameValuePair>(params.size());
            for(Map.Entry<String, String> entry : params.entrySet()){
                NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
                valuePairs.add(nameValuePair);
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, "UTF-8");
            httpPost.setEntity(formEntity);
            HttpResponse resp = client.execute(httpPost);
            
            HttpEntity entity = resp.getEntity();
            String respContent = EntityUtils.toString(entity , "UTF-8").trim();
            httpPost.abort();
            client.getConnectionManager().shutdown();
            return respContent;
            
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
