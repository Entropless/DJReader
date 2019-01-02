package com.wy.djreader.utils.httputil;

import android.provider.FontsContract;

public interface OkHttpUtil {
    //同步请求
    void resStreamSyncGet();

    //异步请求
    //响应流Get请求
    void resStreamAsyncGet(String requestUrl, ReqestCallBack callBack);

    interface ReqestCallBack {
        //请求成功回调
        void requestSuccessful(Object object);
        //请求失败回调
        void requestFailed(Exception e);
    }

}
