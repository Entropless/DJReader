package com.wy.djreader.main.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;

import com.wy.djreader.BuildConfig;
import com.wy.djreader.R;
import com.wy.djreader.databinding.ActivityMainBinding;
import com.wy.djreader.main.MainPageContact;
import com.wy.djreader.main.model.ParseXml;
import com.wy.djreader.main.view.MainActivity;
import com.wy.djreader.main.viewmodel.MainViewModel;
import com.wy.djreader.model.entity.UpdateInfos;
import com.wy.djreader.utils.Constant;
import com.wy.djreader.utils.DialogUtil;
import com.wy.djreader.utils.MessageManager;
import com.wy.djreader.utils.ToastUtil;
import com.wy.djreader.utils.httputil.OkHttpImpl;
import com.wy.djreader.utils.httputil.OkHttpUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainPagePresenter implements MainPageContact.Presenter{

    private MainPageContact.View mainView;
    private WeakReference<MainPageContact.View> mView;
    private WeakReference<MainPagePresenter> mPresenterWeak;
    private UpdateInfos updateInfos;
    private Context context;
    private String currVersionCode;
    private String currVersionName;
    private OkHttpUtil okHttpUtil = new OkHttpImpl();

    private Handler updateHandler = null;

    /**
     * @ClassN UpdateHandler
     * @desc 接收更新信息的Handler
     * @author wy
     * @date 2019/1/3 17:32
     */
    static class UpdateHandler extends Handler{
        private MainPageContact.View mView;
        public UpdateHandler(WeakReference<MainPageContact.View> mView) {
            this.mView = mView.get();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mView == null) return;
            switch (msg.what) {
                case Constant.Flag.UPDATE_CLIENT:
                    Bundle data = msg.getData();
                    //显示更新提示
                    mView.showUpdateDialog(data);
                    break;
                case Constant.Flag.NETWORK_ERROR:
                    mView.showToast((String) msg.obj,ToastUtil.SHORT);
                    break;
                case Constant.Flag.DOWN_ING:
                    Bundle bundle = msg.getData();
                    mView.updateDownloadProgress(bundle);
                    break;
            }
        }
    }

    /**
     * @ClassN MyRunnable
     * @desc 获取更新信息的线程
     * @author wy
     * @date 2019/1/2 9:37
     */
    static class MyRunnable implements Runnable {
        private String getInfoUrl;
        private MainPagePresenter mPresenterWeak;
        private MyRunnable(String getInfoUrl,WeakReference<MainPagePresenter> mPresenterWeak) {
            this.getInfoUrl = getInfoUrl;
            this.mPresenterWeak = mPresenterWeak.get();
            this.mPresenterWeak.updateHandler = new UpdateHandler(this.mPresenterWeak.mView);
        }

        @Override
        public void run() {
            mPresenterWeak.okHttpUtil.asyncGet(getInfoUrl, OkHttpUtil.ReturnType.STREAM, null, new OkHttpUtil.RequestCallback() {
                @Override
                public void requestSuccessful(Object object) {
                    //请求成功，处理返回结果
                    mPresenterWeak.updateInfos = ParseXml.getUpdateInfos((InputStream) object);
                    Bundle infos = new Bundle();
                    infos.putString("versionCode",mPresenterWeak.updateInfos.getVersionCode());
                    infos.putString("versionName",mPresenterWeak.updateInfos.getVersionName());
                    infos.putString("appUpdateUrl",mPresenterWeak.updateInfos.getAppUpdateUrl());
                    infos.putString("description",mPresenterWeak.updateInfos.getDescription());
                    //判断版本号
                    if (!mPresenterWeak.currVersionCode.equals(mPresenterWeak.updateInfos.getVersionCode())) {
                        MessageManager msg = new MessageManager(mPresenterWeak.updateHandler,Constant.Flag.UPDATE_CLIENT,infos);
                        msg.sendMessage();
                    }
                }

                @Override
                public void requestFailed(Exception e) {
                    MessageManager msg = new MessageManager(mPresenterWeak.updateHandler,Constant.Flag.NETWORK_ERROR,e.toString());
                    msg.sendMessage();
                }
            });
        }
    }
    /**
     * @desc 构造
     * @author wy
     * @date 2019/1/7 18:18
     * @params
     * @return
     */
    public MainPagePresenter(MainPageContact.View view, Context context) {
        this.mainView = view;
        this.context = context.getApplicationContext();
        mView = new WeakReference<>(this.mainView);
        mPresenterWeak = new WeakReference<>(this);
    }

    @Override
    public void checkVersionUpdate() {
        String updateInfoUrl = context.getString(R.string.updateInfo_url);
        //获取当前版本号
        currVersionCode = String.valueOf(BuildConfig.VERSION_CODE);
        currVersionName = BuildConfig.VERSION_NAME;
        //访问服务器读取更新信息
        readUpdateInfo(updateInfoUrl);
    }

    @Override
    public void downLoadApk(MainViewModel mainViewModel, ActivityMainBinding mainBinding) {
        //显示下载进度条
        mainView.showDownloadBar();
        //获取下载URL
        String downLoadUrl = updateInfos.getAppUpdateUrl();
        //封装Map
        Map<String,Object> params = new HashMap<>();
        params.put(Constant.FILE_PATH,Constant.DOWNLOAD_PATH);
        params.put(Constant.FILE_NAME,updateInfos.getVersionName()+".apk");
        //开始下载apk文件
        okHttpUtil.asyncGet(downLoadUrl,OkHttpUtil.ReturnType.FILE,params, new OkHttpUtil.RequestCallback() {
            @Override
            public void requestSuccessful(Object object) {
                ResponseBody responseBody = (ResponseBody) object;
                //下载文件需要将下载目录及文件名称封到Map中
                String filePath = (String) params.get(Constant.FILE_PATH);
                String fileName = (String) params.get(Constant.FILE_NAME);
                File folder = new File(filePath);
                File file = null;
                FileOutputStream fout;
                try {
                    boolean hasFolder;
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
                    InputStream in = responseBody.byteStream();
                    long total = responseBody.contentLength();
                    int tot;
                    if (total > Integer.MAX_VALUE){
                        throw new RuntimeException("total is over Integer MaxValue");
                    }else {
                        tot = (int) total;
                    }
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
                        Bundle data = new Bundle();
                        data.putInt("progress",progress);
                        data.putInt("total",tot);
                        MessageManager msg = new MessageManager(updateHandler,Constant.Flag.DOWN_ING,data);
                        msg.sendMessage();
                    }
                    fout.flush();
                    in.close();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed(Exception e) {

            }
        });
    }

    /**
     * @desc 读取更新信息
     * @author wy
     * @date 2019/1/1 10:53
     * @params
     * @return
     */
    private void readUpdateInfo(String updateInfoUrl) {
        //创建并启动获取更新信息的线程
//        Runnable myRunnable = new MyRunnable(updateInfoUrl,mPresenterWeak);
//        Thread getInfoThread = new Thread(myRunnable);
//        getInfoThread.start();
        updateHandler = new UpdateHandler(mView);
        okHttpUtil.asyncGet(updateInfoUrl, OkHttpUtil.ReturnType.STREAM, null, new OkHttpUtil.RequestCallback() {
            @Override
            public void requestSuccessful(Object object) {
                //请求成功，处理返回结果
                updateInfos = ParseXml.getUpdateInfos((InputStream) object);
                Bundle infos = new Bundle();
                infos.putString("versionCode",updateInfos.getVersionCode());
                infos.putString("versionName",updateInfos.getVersionName());
                infos.putString("appUpdateUrl",updateInfos.getAppUpdateUrl());
                infos.putString("description",updateInfos.getDescription());
                //判断版本号
                if (!currVersionCode.equals(updateInfos.getVersionCode())) {
                    MessageManager msg = new MessageManager(updateHandler,Constant.Flag.UPDATE_CLIENT,infos);
                    msg.sendMessage();
                }
            }

            @Override
            public void requestFailed(Exception e) {
                MessageManager msg = new MessageManager(updateHandler,Constant.Flag.NETWORK_ERROR,e.toString());
                msg.sendMessage();
            }
        });
    }

    @Override
    public void onDestroy() {
        //页面销毁时，将view置空
        mainView = null;
        System.gc();
    }
}
