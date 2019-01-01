package com.wy.djreader.utils.httputil;

import okhttp3.OkHttpClient;

public class SingletonOkHttp {
    private OkHttpClient okHttpClient = null;

    private SingletonOkHttp(){
        this.okHttpClient = new OkHttpClient();
    }
    private static class Singleton {
        private static final SingletonOkHttp singletonOkHttp = new SingletonOkHttp();
    }

    public static SingletonOkHttp getInstance(){

        return Singleton.singletonOkHttp;
    }
}
