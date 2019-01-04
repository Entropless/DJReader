package com.wy.djreader.main_page.presenter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.wy.djreader.BuildConfig;
import com.wy.djreader.R;
import com.wy.djreader.main_page.MainPageContact;
import com.wy.djreader.main_page.model.ParseXml;
import com.wy.djreader.model.entity.UpdateInfos;
import com.wy.djreader.utils.Constant;
import com.wy.djreader.utils.MessageManager;
import com.wy.djreader.utils.ToastUtil;
import com.wy.djreader.utils.httputil.OkHttpImpl;
import com.wy.djreader.utils.httputil.OkHttpUtil;

import java.io.InputStream;
import java.lang.ref.WeakReference;

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
            mPresenterWeak.okHttpUtil.resStreamAsyncGet(getInfoUrl, new OkHttpUtil.RequestCallBack() {
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
     * @ClassN DownLoadThread
     * @desc 下载apk的线程
     * @author think
     * @date 2019/1/3 18:14
     */
    static class DownLoadThread implements Runnable{
        private String downloadUrl;
        private MainPagePresenter mPresenterWeak;
        public DownLoadThread(String downloadUrl, WeakReference<MainPagePresenter> mPresenterWeak) {
            this.downloadUrl = downloadUrl;
            this.mPresenterWeak = mPresenterWeak.get();
        }

        @Override
        public void run() {

        }
    }


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
    public void downLoadApk() {
        //获取下载URL
        String downLoadUrl = updateInfos.getAppUpdateUrl();
        //启动线程下载apk文件
        Runnable runnable = new DownLoadThread(downLoadUrl,mPresenterWeak);
        Thread downLoadThread = new Thread(runnable);
        downLoadThread.start();
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
        Runnable myRunnable = new MyRunnable(updateInfoUrl,mPresenterWeak);
        Thread getInfoThread = new Thread(myRunnable);
        getInfoThread.start();
    }

    @Override
    public void onDestroy() {
        //页面销毁时，将view置空
        mainView = null;
        System.gc();
    }
}
