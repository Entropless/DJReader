package com.wy.djreader.utils.httputil;

import com.wy.djreader.utils.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CommonCallback implements Callback {

    private OkHttpUtil.RequestCallback callback;
    private OkHttpUtil.ReturnType returnType;
    private Object object;
    public CommonCallback(OkHttpUtil.RequestCallback callback, OkHttpUtil.ReturnType returnType) {
        this.callback = callback;
        this.returnType = returnType;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        callback.requestFailed(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        object = disposeResponse(response,returnType);

        callback.requestSuccessful(object);
    }

    /**
     * 处理响应
     * @param response
     * @param returnType
     * @return
     */
    private Object disposeResponse(Response response, OkHttpUtil.ReturnType returnType){
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
                InputStream in;
                File file = new File(Constant.DOWNLOAD_PATH);
                FileOutputStream fout;
                try {
                    in = response.body().byteStream();
                    fout = new FileOutputStream(file);
                    byte[] buffer = new byte[2048];
                    int len;
                    while (-1 != (len = in.read(buffer))){
                        fout.write(buffer,0,len);
                    }
                    fout.flush();
                    in.close();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                object = file;
                break;

        }
        return object;
    }
}
