package com.wy.djreader.utils.httputil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateInfoThread extends Thread {

    private String updateInfoUrl = "";
    public UpdateInfoThread(String updateInfoUrl) {
        this.updateInfoUrl = updateInfoUrl;
    }

    @Override
    public void run() {
        super.run();
        //创建OKhttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建Request请求对象
        Request request = new Request.Builder().url(updateInfoUrl).build();
        //创建Response对象
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //okhttpGet


    //OKhttpPost
}
