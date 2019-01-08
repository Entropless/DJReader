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
                MainViewModel mainViewModel = new MainViewModel();
                //下载文件需要将下载目录及文件名称封到Map中
                String filePath = (String) params.get(Constant.FILE_PATH);
                String fileName = (String) params.get(Constant.FILE_NAME);
                InputStream in;
                File folder = new File(filePath);
                File file = null;
                FileOutputStream fout;
                try {
                    boolean hasFolder = false;
                    if (!folder.exists()) {
                        hasFolder = folder.mkdirs();
                        if (!hasFolder) {
                            throw new RuntimeException("create folder is failed!");
                        }
                    }

                    file = new File(filePath + fileName);
                    if (!file.exists()){
                        file.createNewFile();
                    }
                    in = response.body().byteStream();
                    long total = response.body().contentLength();
                    int tot;
                    if (total > Integer.MAX_VALUE){
                        throw new RuntimeException("total is too large over Integer MaxValue");
                    }else {
                        tot = (int) total;
                    }
                    mainViewModel.setDownloadMax(tot);
                    fout = new FileOutputStream(file);
                    byte[] buffer = new byte[2048];
                    int len;
                    long now = 0;//当前下载大小
                    int progress = 0;//进度
                    BigDecimal bigDecimal = null;
                    while (-1 != (len = in.read(buffer))){
                        fout.write(buffer,0,len);
                        now += len;
                        float percent = (float) now / total;
                        bigDecimal = new BigDecimal(percent);
                        percent = bigDecimal.setScale(2, BigDecimal.ROUND_CEILING).floatValue();
                        progress = (int) (percent * 100);
                        mainViewModel.setProgress(progress);
                     }
                    fout.flush();
                    in.close();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    object = file;
                }
                break;
        }
        return object;
    }
}
