package com.turo.nasusweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by YQ950209 on 2017/2/14.
 */
//访问网络工具类，处理服务器响应
public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
