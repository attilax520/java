package com.attilax.coreLuni.net;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class httpclientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s = "http://192.168.1.98:8085/onehismq/Mq/MQ/LogSysMQ?value=@v@&topicName=lou";
		String r = new httpclientTest().httpget(s);
		System.out.println(r);

		// HttpClient 4.5.3 get和post请求 - 咸鱼Boy - 博客园.html

	}

	private String httpget(String s) {
		CloseableHttpClient httpCilent = HttpClients.createDefault();// Creates
																		// CloseableHttpClient
																		// instance
																		// with
																		// default
																		// configuration.
		HttpGet httpGet = new HttpGet(s);

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
}
