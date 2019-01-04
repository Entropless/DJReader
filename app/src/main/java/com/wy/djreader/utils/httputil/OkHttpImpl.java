package com.wy.djreader.utils.httputil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class OkHttpImpl implements OkHttpUtil{

    private SingletonOkHttp singletonOkHttp = SingletonOkHttp.getInstance();

    @Override
    public void resStreamSyncGet() {

    }

    @Override
    public void resStreamAsync(String requestUrl, MethodType methodType, Map<String,Object> params, RequestCallBack callBack) {
        //创建Request
        Request request = null;
        if (methodType == MethodType.GET){
            request = CreateRequest.createGetRequest(requestUrl,params);
        }else if (methodType == MethodType.POST){
            request = CreateRequest.createPostRequest(requestUrl,params);
        }
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

    @Override
    public void resFileAsyncPost(String downLoadUrl, RequestCallBack callBack) {

    }

}
