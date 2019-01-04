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
    private CommonCallback commonCallback;

    @Override
    public void resStreamSyncGet() {

    }

    @Override
    public void resStreamAsync(String requestUrl, MethodType methodType, Map<String,Object> params, RequestCallback callBack) {
        //创建Request
        Request request = null;
        if (methodType == MethodType.GET){
            request = CreateRequest.createGetRequest(requestUrl,params);
        }else if (methodType == MethodType.POST){
            request = CreateRequest.createPostRequest(requestUrl,params);
        }
        //创建共用的Callback
        commonCallback = new CommonCallback(callBack);
        //创建Call对象
        CreateCall.asyncCall(request,commonCallback);
    }

    @Override
    public void resFileAsync(String downLoadUrl, MethodType methodType, RequestCallback callBack) {

    }

}
