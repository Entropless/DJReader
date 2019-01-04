package com.wy.djreader.utils.httputil;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CommonCallback implements Callback {

    private OkHttpUtil.RequestCallback callback;
    public CommonCallback(OkHttpUtil.RequestCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        callback.requestFailed(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        response.body();
    }
}
