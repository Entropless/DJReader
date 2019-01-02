package com.wy.djreader.utils.httputil;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpImpl implements OkHttpUtil{

    private SingletonOkHttp singletonOkHttp = SingletonOkHttp.getInstance();

    @Override
    public void resStreamSyncGet() {

    }

    @Override
    public void resStreamAsyncGet(String requestUrl, ReqestCallBack callBack) {
        //设置超时时间
        singletonOkHttp.okHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);
        //创建Request对象
        Request request = new Request.Builder().url(requestUrl).build();
        //创建Call对象，将request传入
        singletonOkHttp.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.requestFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream in = response.body().byteStream();
                callBack.requestSuccessful(in);
            }
        });
    }
}
