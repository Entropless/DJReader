package com.wy.djreader.utils.httputil;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;

public class CreateRequest {

    /**
     * @desc 创建GET类型请求
     * @author wy
     * @date 2019/1/4 16:31
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, Map<String,Object> params){
        //拼接get请求的URL
        Request.Builder builder = new Request.Builder();
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null){
            for (Map.Entry<String,Object> entry: params.entrySet()) {
                urlBuilder.append(entry.getKey())
                          .append("=")
                          .append(entry.getValue())
                          .append("&");
            }
            builder.url(urlBuilder.substring(0,urlBuilder.length()-1));
        }
        builder.url(url);
        return builder.build();
    }

    /**
     * @desc 创建POST类型的请求
     * @author wy
     * @date 2019/1/4 16:41
     * @param url
     * @param params
     * @return
     */
    public static Request createPostRequest(String url, OkHttpUtil.CommitType commitType, Map<String, Object> params){
        Request request = null;
        //判断提交的数据类型
        switch (commitType) {
            case FORM:
                FormBody.Builder formBuilder = new FormBody.Builder();
                if (params != null) {
                    for (Map.Entry<String,Object> entry : params.entrySet()) {
                        formBuilder.add(entry.getKey(), (String) entry.getValue());
                    }
                }
                FormBody formBody = formBuilder.build();
                request = new Request.Builder().url(url).post(formBody).build();
                break;
            case STREAM:

                break;
            case FILE:

                break;
            case JSON:

                break;
        }
        return request;
    }
}
