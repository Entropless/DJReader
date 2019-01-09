package com.wy.djreader.utils.httputil;

import com.wy.djreader.main.viewmodel.MainViewModel;
import com.wy.djreader.utils.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class CommonCallback implements Callback {

    private OkHttpUtil.RequestCallback callback;
    private Map<String,Object> params;
    private OkHttpUtil.ReturnType returnType;
    private Object object;
    public CommonCallback(OkHttpUtil.RequestCallback callback, Map<String,Object> params, OkHttpUtil.ReturnType returnType) {
        this.callback = callback;
        this.params = params;
        this.returnType = returnType;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        callback.requestFailed(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        object = disposeResponse(response,params,returnType);

        callback.requestSuccessful(object);
    }

    /**
     * 处理响应
     * @param response
     * @param returnType
     * @return
     */
    private Object disposeResponse(Response response, Map<String,Object> params, OkHttpUtil.ReturnType returnType){
        switch (returnType){
            case JSON:

                break;
            case STREAM:
                object = response.body().byteStream();
                break;
            case STRING:
                object = response.body().toString();
                break;
            case FILE:
                ResponseBody responseBody = response.body();
                object = responseBody;
                break;
        }
        return object;
    }
}
