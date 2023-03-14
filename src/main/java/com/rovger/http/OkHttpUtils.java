package com.rovger.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description: okhttp3封装通用http工具类
 * @author: weijie.lu
 * @version: 1.0.0
 * @createTime: 2023年03月08日 15:50
 */
@Slf4j
public class OkHttpUtils {

    public static void main(String[] args) {
        Map<String, Object> params = new TreeMap<>();
        params.put("content", "今天星期几？");

        Map<String, Object> headers = new TreeMap<>();
        params.put("Content-Type", "application/json");

        String str = OkHttpUtils.get("https://httpbin.org/get", params, headers, String.class);
        log.info(str);
    }

    private static final MediaType MediaType_JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
            .build();

    /**
     * get请求
     *
     * @param url
     * @param params
     * @param headers
     */
    public static <T> T get(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> clazz) {
        //拼接参数
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //构建请求
        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());
        //添加头部信息
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //执行请求
        try {
            Response response = okHttpClient.newCall(requestBuilder.build()).execute();
            return result(response, clazz);
        } catch (IOException e) {
            throw new RuntimeException("call OkHttpUtils::get() failed", e);
        }
    }

    /**
     * get请求(异步回调)
     *
     * @param url
     * @param params
     * @param headers
     * @param callback
     */
    public static void getAsync(String url, Map<String, Object> params, Map<String, Object> headers, Callback callback) {
        //拼接参数
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //构建请求
        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());
        //添加头部信息
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //执行请求
        okHttpClient.newCall(requestBuilder.build()).enqueue(callback);
    }

    /**
     * post请求（表单提交）
     *
     * @param url
     * @param params
     * @param headers
     * @param clazz
     */
    public static <T> T post(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> clazz) {
        //构建表单数据
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //构建请求
        Request.Builder requestBuilder = new Request.Builder().url(url).post(formBuilder.build());
        //添加头部信息
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //执行请求
        try {
            Response response = okHttpClient.newCall(requestBuilder.build()).execute();
            return result(response, clazz);
        } catch (IOException e) {
            throw new RuntimeException("call OkHttpUtils::post() failed", e);
        }
    }

    /**
     * post请求（Json字符串）
     *
     * @param url
     * @param jsonParams
     * @param headers
     * @param clazz
     */
    public static <T> T postJson(String url, String jsonParams, Map<String, Object> headers, Class<T> clazz) {
        //创建RequestBody对象
        RequestBody requestBody = RequestBody.create(MediaType_JSON, jsonParams);
        //构建请求
        Request.Builder requestBuilder = new Request.Builder().url(url).post(requestBody);
        //添加头部信息
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //执行请求
        try {
            Response response = okHttpClient.newCall(requestBuilder.build()).execute();
            return result(response, clazz);
        } catch (IOException e) {
            throw new RuntimeException("call OkHttpUtils::postJson() failed", e);
        }
    }

    /**
     * post请求(异步回调)
     *
     * @param url
     * @param params
     * @param headers
     * @param callback
     */
    public static void postAsync(String url, Map<String, Object> params, Map<String, Object> headers, Callback callback) {
        //构建表单数据
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //构建请求
        Request.Builder requestBuilder = new Request.Builder().url(url).post(formBuilder.build());
        //添加头部信息
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //执行请求
        okHttpClient.newCall(requestBuilder.build()).enqueue(callback);
    }

    private static <T> T result(Response response, Class<T> clazz) throws IOException {
        Optional<T> tOptional = Optional.ofNullable(response)
                .filter(Response::isSuccessful)
                .map(Response::body)
                .map(body -> {
                    try {
                        return body.string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(StringUtils::isNotBlank)
                .map(body -> JSON.parseObject(body, clazz));
        if (!tOptional.isPresent()) {
            throw new RuntimeException("OkHttpUtils::result() failed, responseBody:\n" + response.body().string());
        }
        return tOptional.get();
    }
}
