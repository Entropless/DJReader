package com.wy.djreader.utils.httputil;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateCall {
    private static OkHttpClient okHttpClient = SingletonOkHttp.getInstance().okHttpClient;
    private static Call call;
    public static Call syncCall(Request request) throws IOException {
        call = okHttpClient.newCall(request);
        call.execute();
        return call;
    }

    /**
     * 发送异步请求
     * @param request
     * @param callback
     * @return
     */
    public static Call asyncCall(Request request, CommonCallback callback) {
        call = okHttpClient.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
