package com.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClientUtil {

    private static HttpClientUtil ins;

    public static HttpClientUtil getInstance() {
        if (ins == null) {
            synchronized (HttpClientUtil.class) {
                if (ins == null) {
                    ins = new HttpClientUtil();
                }
            }
        }
        return ins;
    }

//	String originalUrl = url + "?" + Joiner.on("&").withKeyValueSeparator("=").join(params);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    public String request(String url, String method, Map<String, String> params, Map<String, String> headers) {
        RequestBody requestBody = null;
        System.out.println(LocalDateTime.now() + " get url: " + url);
        if (params.isEmpty()) {
            requestBody = RequestBody.Companion.create("", JSON_TYPE);
        } else {
            Gson gson = new Gson();
            requestBody = RequestBody.Companion.create(gson.toJson(params), JSON_TYPE);
            System.out.println(LocalDateTime.now() + "post body: " + gson.toJson(params));
        }

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.addHeader("Content-Type", "application/json");
        if (headers != null && !headers.isEmpty()) {
            Set<String> col = headers.keySet();
            Iterator<String> it = col.iterator();
            while (it.hasNext()) {
                String key = it.next();
                requestBuilder.addHeader(key, headers.get(key));
            }
        }

        Request request;

//		System.out.println(requestBuilder.getHeaders$okhttp().get("key"));
//		System.out.println(requestBuilder.getHeaders$okhttp().get("timestamp"));
//		System.out.println(requestBuilder.getHeaders$okhttp().get("sign"));
//		System.out.println(requestBuilder.getHeaders$okhttp().get("clientSign"));

        if ("GET".equalsIgnoreCase(method)) {
            request = requestBuilder.url(url).build();
        } else if ("POST".equalsIgnoreCase(method)) {
            request = requestBuilder.url(url).post(requestBody).build();
        } else if ("DELETE".equalsIgnoreCase(method)) {
            request = requestBuilder.url(url).delete().build();
        } else {
            throw new RuntimeException("not supported http method");
        }

        String body = null;
        try (Response response = okHttpClient.newCall(request).execute()) {
            body = response.body().string();
            System.out.println(LocalDateTime.now() + "post body: " + body);
            return body;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//		System.out.println(body);
        return body;
    }

}
