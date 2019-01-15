package com.wy.djreader.main;

import android.os.Bundle;

import com.wy.djreader.base.BasePresenter;
import com.wy.djreader.base.BaseView;

public interface MainPageContact {
    interface View extends BaseView {
        //显示更新提示
        void showUpdateDialog(Bundle data);
        //显示下载进度条
        void showDownloadBar();
        //更新下载进度
        void updateDownloadProgress(Bundle data);
        //隐藏进度条
        void hideProgressBar();
        //显示Toast
        void showToast(String msg, int showTime);
    }

    interface Presenter extends BasePresenter{
        //检查版本更新
        void checkVersionUpdate(boolean isUpdating);
        //获取更新的信息
        Bundle getUpdateInfos();
        //下载apk
        void downLoadApk();
        //
    }
}
