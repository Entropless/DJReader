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
    public Object syncGet(String requestUrl, ReturnType returnType, Map<String, Object> params) {
        Request request = CreateRequest.createGetRequest(requestUrl,params);
        Response response = CreateCall.syncCall(request);
        //处理不同的返回值类型
        Object object = disposeResponse(response, returnType);
        return object;
    }

    @Override
    public Object syncPost(String requestUrl, CommitType commitType, ReturnType returnType, Map<String, Object> params) {
        Object object = null;

        return object;
    }

    /**
     * 处理响应
     * @param response
     * @param returnType
     * @return
     */
    private Object disposeResponse(Response response, ReturnType returnType) {
        Object object = null;
        switch (returnType) {
            case JSON:

                break;
            case STREAM:
                object = response.body().byteStream();
                break;
            case STRING:
                object = response.body().toString();
                break;
            case FILE:

                break;
        }
        return object;
    }

    @Override
    public void asyncGet(String requestUrl, ReturnType returnType, Map<String,Object> params, RequestCallback callBack) {
        //创建Request
        Request request = CreateRequest.createGetRequest(requestUrl,params);
        //创建共用的Callback
        commonCallback = new CommonCallback(callBack,returnType);
        //创建Call对象
        CreateCall.asyncCall(request,commonCallback);
    }

    @Override
    public void asyncPost(String requestUrl, CommitType commitType, ReturnType returnType, Map<String,Object> params, RequestCallback callBack) {
        Request request = CreateRequest.createPostRequest(requestUrl,commitType,params);
        commonCallback = new CommonCallback(callBack,returnType);
        CreateCall.asyncCall(request,commonCallback);
    }



}
