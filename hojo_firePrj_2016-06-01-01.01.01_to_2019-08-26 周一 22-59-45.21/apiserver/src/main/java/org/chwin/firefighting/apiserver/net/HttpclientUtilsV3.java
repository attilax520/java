package org.chwin.firefighting.apiserver.net;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.HttpEntity;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.chwin.firefighting.apiserver.warning.WarningManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class HttpclientUtilsV3 {
	public static void main(String[] args) throws Exception {
		//test
		System.out.println(HttpclientUtilsV3.get("http://www.baidu.com"));
	}

    private static final Logger logger = LoggerFactory.getLogger(HttpclientUtilsV3.class);

    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslConnectionSocketFactory;
    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = null;
    private static SSLContextBuilder sslContextBuilder = null;

	static {
		try {
			sslContextBuilder = new SSLContextBuilder();
			// 全部信任 不做身份鉴定
			sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s)
						throws CertificateException {
					return true;
				}
			});
			sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build(),
					new String[]{"SSLv3", "TLSv1", "TLSv1.2"}, null,
					NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder
					.<ConnectionSocketFactory>create()
					.register(HTTP, new PlainConnectionSocketFactory())
					.register(HTTPS, sslConnectionSocketFactory).build();
			poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
			poolingHttpClientConnectionManager.setMaxTotal(200);// max
			// connection
		} catch (Exception e) {
			logger.error("httpsclient初始化失败", e);
		}
		sslConnectionSocketFactory = null;
	}

    /**
     * http post请求
     * 
     * @param url
     * @param requestData
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, String requestData)
            throws ClientProtocolException, IOException {
        CloseableHttpClient client = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        if (!StringUtils.isEmpty(requestData)) {
            StringEntity data = new StringEntity(requestData, "UTF-8");
	//            data.setContentType("text/json");
	//            data.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(data);
        }
        CloseableHttpResponse response = client.execute(httpPost);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity, "UTF-8");
        } else {
            return "";
        }
    }

    /**
     * http get请求
     * 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String get(String url) throws ClientProtocolException, IOException {
        CloseableHttpClient client = getHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(org.apache.http.protocol.HTTP.CONTENT_TYPE, "application/json");

        CloseableHttpResponse response = client.execute(httpGet);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity, "UTF-8");
        } else {
            return "";
        }
    }

    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setConnectionManagerShared(true).build();
        return httpClient;
    }
    
    /**
	 * 发送post请求
	 * 
	 * @param url
	 * @param header
	 * @param body
	 * @return
	 */
	public static String doPost(String url, Map<String, String> header, String body) {
		String result = "";
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			// 设置 url
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			// 设置 header
			for (String key : header.keySet()) {
				connection.setRequestProperty(key, header.get(key));
			}
			// 设置请求 body
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			// 保存body
			out.print(body);
			// 发送body
			out.flush();
			// 获取响应body
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @param header
	 * @return
	 */
	public static String doGet(String url, Map<String, String> header) {
		String result = "";
		BufferedReader in = null;
		try {
			// 设置 url
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			// 设置 header
			for (String key : header.keySet()) {
				connection.setRequestProperty(key, header.get(key));
			}
			// 设置请求 body
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			return null;
		}
		return result;
	}



	/**
	 * 发送post请求,根据Content-Type分别返回不同的返回值
	 * 
	 * @param url
	 * @param header
	 * @param body
	 * @return
	 */
	public static Map<String, Object> doMultiPost(String url, Map<String, String> header, String body) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PrintWriter out = null;
		try {
			// 设置 url
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			// 设置 header
			for (String key : header.keySet()) {
				connection.setRequestProperty(key, header.get(key));
			}
			// 设置请求 body
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			// 保存body
			out.print(body);
			// 发送body
			out.flush();
			// 获取响应header
			String responseContentType = connection.getHeaderField("Content-Type");
			if ("audio/mpeg".equals(responseContentType)){
				// 获取响应body
				byte[] bytes = toByteArray(connection.getInputStream());
				resultMap.put("Content-Type", "audio/mpeg");
				resultMap.put("sid", connection.getHeaderField("sid"));
				resultMap.put("body", bytes);
				return resultMap;
			} else {
				// 设置请求 body
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				String result = "";
				while ((line = in.readLine()) != null) {
					result += line;
				}
				resultMap.put("Content-Type", "text/plain");
				resultMap.put("body", result);
				
				return resultMap;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	private static byte[] toByteArray(InputStream in) throws IOException {
		int count = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[8 * 1024 * 500];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		in.close();
		out.flush();
		return out.toByteArray();
	}


	/**
	 * http post请求
	 *
	 * @param url
	 * @param requestData
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postJson(String url, String requestData)
			throws ClientProtocolException, IOException {
		CloseableHttpClient client = getHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		if (!StringUtils.isEmpty(requestData)) {
			StringEntity data = new StringEntity(requestData, "UTF-8");
//			data.setContentType("text/json");
//			data.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httpPost.setEntity(data);
		}
		CloseableHttpResponse response = client.execute(httpPost);

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			return EntityUtils.toString(entity, "UTF-8");
		} else {
			return "";
		}
	}
}
