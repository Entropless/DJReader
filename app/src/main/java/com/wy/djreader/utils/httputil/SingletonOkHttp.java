package com.wy.djreader.utils.httputil;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SingletonOkHttp {
    public OkHttpClient okHttpClient;

    private SingletonOkHttp(){
        okHttpClient = new OkHttpClient();
        //设置超时时间
        okHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS);
    }
    private static class Singleton {
        private static final SingletonOkHttp singletonOkHttp = new SingletonOkHttp();
    }

    public static SingletonOkHttp getInstance(){
        return Singleton.singletonOkHttp;
    }

}
