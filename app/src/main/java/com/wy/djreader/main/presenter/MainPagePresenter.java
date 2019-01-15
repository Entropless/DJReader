package com.wy.djreader.main.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wy.djreader.BuildConfig;
import com.wy.djreader.R;
import com.wy.djreader.main.MainPageContact;
import com.wy.djreader.main.model.ParseXml;
import com.wy.djreader.model.entity.UpdateInfos;
import com.wy.djreader.utils.Constant;
import com.wy.djreader.utils.MessageManager;
import com.wy.djreader.utils.ToastUtil;
import com.wy.djreader.utils.fileutil.FileOperation;
import com.wy.djreader.utils.httputil.OkHttpImpl;
import com.wy.djreader.utils.httputil.OkHttpUtil;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;

public class MainPagePresenter implements MainPageContact.Presenter{

    private MainPageContact.View mainView;
    private WeakReference<MainPageContact.View> mView;
    private WeakReference<MainPagePresenter> mPresenterWeak;
    private UpdateInfos updateInfos;
    private Context context;
    private String currVersionCode;
    private String currVersionName;
    private Bundle updateBundle = new Bundle();//更新的内容封装为bundle
    private boolean isUpdating = false;
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
            Bundle bundle;
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
                    bundle = msg.getData();
                    mView.updateDownloadProgress(bundle);
                    break;
                case Constant.Flag.DOWN_OK:
                    bundle = msg.getData();
                    mView.updateDownloadProgress(bundle);
                    mView.hideProgressBar();
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
            //同步阻塞请求
           Object object =  mPresenterWeak.okHttpUtil.syncGet(getInfoUrl, OkHttpUtil.ReturnType.STREAM, null);
           InputStream inputStream = (InputStream) object;
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
        //判断当前是否正在更新
        if (!isUpdating){
            //访问服务器读取更新信息
            readUpdateInfo(updateInfoUrl);
        }
    }

    @Override
    public Bundle getUpdateInfos() {
        return updateBundle;
    }


    @Override
    public void downLoadApk() {
        //显示下载进度条
        mainView.showDownloadBar();
        //获取下载URL
        String downLoadUrl = updateBundle.getString("appUpdateUrl");
        //开始下载apk文件
        okHttpUtil.asyncGet(downLoadUrl,OkHttpUtil.ReturnType.FILE,null, new OkHttpUtil.RequestCallback() {
            @Override
            public void requestSuccessful(Object object) {
                ResponseBody responseBody = (ResponseBody) object;
                InputStream inputStream = responseBody.byteStream();
                long contentLength = responseBody.contentLength();
                String fileName = updateBundle.getString("versionName")+".apk";
                boolean isSuccessful = FileOperation.writeToFile(inputStream,contentLength,Constant.DOWNLOAD_PATH,fileName,true,updateHandler);
                if (isSuccessful){
                    isUpdating = false;
                    //TODO 自动提示安装

                }
            }

            @Override
            public void requestFailed(Exception e) {
                MessageManager msg = new MessageManager(updateHandler,Constant.Flag.NETWORK_ERROR);
                msg.sendMessage();
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
        isUpdating = true;
        updateHandler = new UpdateHandler(mView);
        //异步请求
        okHttpUtil.asyncGet(updateInfoUrl, OkHttpUtil.ReturnType.STREAM, null, new OkHttpUtil.RequestCallback() {
            @Override
            public void requestSuccessful(Object object) {
                //请求成功，处理返回结果
                updateInfos = ParseXml.getUpdateInfos((InputStream) object);
                updateBundle.putString("versionCode",updateInfos.getVersionCode());
                updateBundle.putString("versionName",updateInfos.getVersionName());
                updateBundle.putString("appUpdateUrl",updateInfos.getAppUpdateUrl());
                updateBundle.putString("description",updateInfos.getDescription());
                updateBundle.putBoolean("isUpdating",isUpdating);
                //判断版本号
                if (!currVersionCode.equals(updateInfos.getVersionCode())) {
                    MessageManager msg = new MessageManager(updateHandler,Constant.Flag.UPDATE_CLIENT,updateBundle);
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
