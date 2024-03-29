package com.rovger.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: httpClient封装
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2022年09月23日 18:08
 */
public class HttpClientUtils {

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
        params.put("sig", "w7nsAGDbffwpTB79xPkb072ocqzWmOzXoL7grbZi");

        HttpClientUtils httpClientUtils = new HttpClientUtils();
        String resp = httpClientUtils.get(url, params, null);
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

    public String postForm(String url, Map<String, String> params, Map<String, String> headers) throws IOException {
        HttpPost request = new HttpPost(url);
        if (params != null) {
            List<BasicNameValuePair> postData = new ArrayList<>();
            params.entrySet().stream().forEach(entry -> postData.add(new BasicNameValuePair(entry.getKey(), entry.getValue())));
            request.setEntity(new UrlEncodedFormEntity(postData));
        }
        return send(request, headers);
    }

    public String postJsonStr(String url, String jsonStr, Map<String, String> headers) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        try {
            if (null != headers && !headers.isEmpty()) {
                headers.keySet().forEach(key -> httpPost.setHeader(key, headers.get(key)));
            }
            StringEntity stringEntity = new StringEntity(jsonStr);
            stringEntity.setContentEncoding("UTF-8");
            //发送json数据需要设置contentType
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return send(httpPost, headers);
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
