package org.chwin.firefighting.apiserver.core.util;

import okhttp3.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {
    static OkHttpClient client = new OkHttpClient();

    /**
     * json请求
     * @param url
     * @param data
     * @return
     * @throws IOException
     */
    public static Response postJson(String url , String data) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType,data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * json请求
     * @param url
     * @param data
     * @param connnectTimeout 连接超时时长
     * @param readTimeout 读取超时时长
     * @return
     * @throws IOException
     */
    public static Response postJson(String url , String data , long connnectTimeout , long readTimeout) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connnectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout,TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType,data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    public static Response doGet(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }
}
