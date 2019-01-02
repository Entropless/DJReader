package com.wy.djreader.main_page.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.wy.djreader.BuildConfig;
import com.wy.djreader.R;
import com.wy.djreader.main_page.MainPageContract;
import com.wy.djreader.main_page.model.ParseXml;
import com.wy.djreader.utils.Constant;
import com.wy.djreader.utils.MessageManager;
import com.wy.djreader.utils.httputil.OkHttpImpl;
import com.wy.djreader.utils.httputil.OkHttpUtil;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class MainPagePresenter implements MainPageContract.Presenter{

    private MainPageContract.View mainView;
    private WeakReference<MainPageContract.View> mView;
    private Context context;
    private String updateInfoUrl = "";
    private String currVersionCode = "";
    private String currVersionName = "";
    private OkHttpUtil okHttpUtil = new OkHttpImpl();


    private Handler updateHandler = null;
    static class UpdateHandler extends Handler{
        private MainPageContract.View mView;
        public UpdateHandler(WeakReference<MainPageContract.View> mView) {
            this.mView = (MainPageContract.View) mView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.Flag.UPDATE_CLIENT:
                    //显示更新提示
                    //将信息封装成map
                    Map<String,String> map = new HashMap<>();

                    mView.showUpdateDialog();
                    break;
            }
        }
    }

    class MyRunnable implements Runnable {
        private String getInfoUrl;
        public MyRunnable(String getInfoUrl) {
            this.getInfoUrl = getInfoUrl;
        }

        @Override
        public void run() {
            okHttpUtil.resStreamAsyncGet(getInfoUrl, new OkHttpUtil.ReqestCallBack() {
                @Override
                public void requestSuccessful(Object object) {
                    //请求成功，处理返回结果
                    ParseXml.getUpdateInfos((InputStream) object);
                    //向主线程发送消息
                    updateHandler = new UpdateHandler(mView);
                    MessageManager.sendMessage(updateHandler,Constant.Flag.UPDATE_CLIENT,0,null);
                }

                @Override
                public void requestFailed(Exception e) {

                }
            });
        }
    }


    public MainPagePresenter(MainPageContract.View view,Context context) {
        this.mainView = view;
        this.context = context;
        mView = new WeakReference<>(view);
    }

    @Override
    public void checkVersionUpdate() {
        updateInfoUrl = context.getString(R.string.updateInfo_url);
        //获取当前版本号
        currVersionCode = String.valueOf(BuildConfig.VERSION_CODE);
        currVersionName = BuildConfig.VERSION_NAME;
        //访问服务器读取更新信息
        readUpdateInfo(updateInfoUrl);
    }

    /**
     * @desc 读取更新信息
     * @author think
     * @date 2019/1/1 10:53
     * @params
     * @return
     */
    private void readUpdateInfo(String updateInfoUrl) {
        //创建并启动获取更新信息的线程
        Runnable myRunnable = new MyRunnable(updateInfoUrl);
        Thread getInfoThread = new Thread(myRunnable);
        getInfoThread.start();
    }
}
