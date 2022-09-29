package com.rovger.http;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年09月23日 18:08
 */
public class HttpClient {

    public static void main(String[] args) throws IOException {
        String url = "https://api.ximalaya.com/v2/search/albums";
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("count", "10");
        params.put("sort_by", "play_count");
        params.put("nonce", "59966843722868189481105471083441");
        params.put("title", "郭德纲");
        params.put("server_api_version", "1.0.0");
        params.put("device_id", "wJv5_888");
        params.put("app_key", "ffc0071ed165ec9e316253da049059b6");
        params.put("client_os_type", "4");
        params.put("sig", "w7nsAGDbffwpTB79xPkb072ocqzWmOzXoL7grbZi+Qun9TYk3Vmsk1Tm9pmbACU+uVObWeJYDJUK8R9fOf+1Zu2Aul8FvT5Ae9E+AG0Rjd9EV6l5ntNE+7x863eAamOdICLglKiybFoncSrrsaU//G0kwQFyaf+W+pYGFxsdFC8VeuhmXtV5urQ8ezZdcC0ZEwjVA8linz3iWQ073y6K+fXUnDAnXoF61mIWkfyT5Es9TCTrElZ96+B48YtiXQ5L59iBjlIvtH11UGf+RsC2tO9kzT2PX/EX9qbgwsJIWqHCeqitk1S85wnwcqpsPLm9UMLBjOc6AS9Y9/lDR/KE5g==");

        HttpClient httpClient = new HttpClient();
        String resp = httpClient.get(url, params, null);
        System.out.println(resp);
    }

    public String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        StringBuilder dataBuilder = new StringBuilder();
        if (params != null) {
            params.entrySet().stream().forEach(entry -> dataBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));
            dataBuilder.deleteCharAt(dataBuilder.length() - 1);
        }

        if (url.indexOf("?") == -1) {
            url += "?" + dataBuilder;
        } else {
            url += "&" + dataBuilder;
        }
        System.out.println("get url: " + url);
        HttpGet request = new HttpGet(url);
        return send(request, headers);
    }

    public String post(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        HttpPost request = new HttpPost(url);
        if (params != null) {
            List<BasicNameValuePair> postData = new ArrayList<>();
            params.entrySet().stream().forEach(entry -> postData.add(new BasicNameValuePair(entry.getKey(), entry.getValue())));
            request.setEntity(new UrlEncodedFormEntity(postData));
        }
        return send(request, headers);
    }

    private String send(HttpRequestBase request, Map<String, String> headers) throws IOException {
        String responseBody = "";
        if (headers != null) {
            headers.entrySet().stream().forEach(entry -> request.setHeader(entry.getKey(), entry.getValue()));
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        if (entity != null) {
            long length = entity.getContentLength();
            if (length != -1 && length < 2048) {
                responseBody = EntityUtils.toString(entity);
            } else {
                InputStream in = entity.getContent();
                byte[] data = new byte[4096];
                int count;
                while ((count = in.read(data, 0, 4096)) != -1) {
                    outStream.write(data, 0, count);
                }
                responseBody = new String(outStream.toByteArray(), "UTF-8");
            }
        }
        return responseBody;
    }
}
