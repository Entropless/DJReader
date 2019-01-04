package com.wy.djreader.utils.httputil;

import java.lang.invoke.MethodType;
import java.util.Map;

public interface OkHttpUtil {
    /*同步请求*/
    void resStreamSyncGet();

    /*异步请求*/
    //响应流Get请求
    void resStreamAsync(String requestUrl, MethodType methodType, Map<String,Object> params, RequestCallback callback);
    //请求下载文件
    void resFileAsync(String downLoadUrl, MethodType methodType, RequestCallback callBack);

    interface RequestCallback {
        //请求成功回调
        void requestSuccessful(Object object);
        //请求失败回调
        void requestFailed(Exception e);
    }

    /**
     * 请求方式
     */
    enum MethodType{
        GET,POST
    }

    enum RequestType{
        //json串，表单，流，上传文件， 下载文件
        JSON, FORM, STREAM, DOWN_FILE, UP_FILE
    }

}
