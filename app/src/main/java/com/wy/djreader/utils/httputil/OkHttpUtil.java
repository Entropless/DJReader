package com.wy.djreader.utils.httputil;

import java.util.Map;

public interface OkHttpUtil {
    /*同步请求*/
    //Get请求
    Object syncGet(String requestUrl, ReturnType returnType, Map<String,Object> params);
    //Post请求
    Object syncPost(String requestUrl, CommitType commitType, ReturnType returnType, Map<String,Object> params);

    /*异步请求*/
    //Get请求
    void asyncGet(String requestUrl, ReturnType returnType, Map<String,Object> params, RequestCallback callback);
    //Post请求
    void asyncPost(String requestUrl, CommitType commitType, ReturnType returnType, Map<String,Object> params, RequestCallback callBack);

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

    enum CommitType{
        //json串，表单，流，上传文件
        JSON, FORM, STREAM, FILE
    }

    //返回内容类型
    enum ReturnType{
        // json，流，字符串，文件
        JSON, STREAM, STRING, FILE
    }

}
