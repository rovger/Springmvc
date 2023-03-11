package com.rovger.utils;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            };

            final TrustManager[] trustAllCerts = new TrustManager[]{
                    x509TrustManager
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                    .hostnameVerifier((String hostname, SSLSession session) -> true)
                    .connectTimeout(1000, TimeUnit.MILLISECONDS)
                    .readTimeout(1500, TimeUnit.MILLISECONDS)
                    .writeTimeout(1000, TimeUnit.MILLISECONDS)
                    .build();

            return okHttpClient;
        } catch (Exception e) {
            log.error("创建okHttpClient失败, msg:", e);
            throw new RuntimeException(e);
        }
    }

    private static OkHttpClient client = getUnsafeOkHttpClient();

    public static Response post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static Response post(String url, String json, Map<String, String> headerMap) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Headers headers = Headers.of(headerMap);
        Request request = new Request.Builder()
                .headers(headers)
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public static Response postForm(String url, Map<String, String> headerMap, FormBody formBody) throws IOException {
        Headers headers = Headers.of(headerMap);
        Request request = new Request.Builder()
                .headers(headers)
                .url(url)
                .post(formBody)
                .build();
        return client.newCall(request).execute();
    }

    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute();
    }

    public static void main(String[] args) throws IOException {
        String url = "http://cms.9nali.com/openapi-tech-support-toolkit/resync/tracks";

        Map<String, String> params = new HashMap<>();
        params.put("ids", "600482871");

        JSON json = JSONUtil.parse(params);
        Response post = OkHttpUtils.get(url);

        System.out.println(post.body().string());
    }

}