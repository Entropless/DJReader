package com.wy.djreader.utils.httputil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateCall {
    private static OkHttpClient okHttpClient = SingletonOkHttp.getInstance().okHttpClient;
    private static Call call;
    public static Call syncCall(Request request) throws IOException {
        call = okHttpClient.newCall(request);
        call.execute();
        return call;
    }

    public static Call asyncCall(Request request, OkHttpUtil.RequestCallBack callBack) {
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.requestFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                callBack.requestSuccessful();
            }
        });
        return call;
    }

    static class JsonCallBack implements Callback{

        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

        }
    }
}
