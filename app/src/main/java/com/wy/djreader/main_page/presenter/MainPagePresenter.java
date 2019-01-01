package com.wy.djreader.main_page.presenter;

import android.content.Context;

import com.wy.djreader.BuildConfig;
import com.wy.djreader.R;
import com.wy.djreader.main_page.MainPageContract;
import com.wy.djreader.utils.httputil.UpdateInfoThread;

public class MainPagePresenter implements MainPageContract.Presenter{

    private MainPageContract.View mainView;
    private Context context;
    private String updateInfoUrl = "";
    private String currVersionCode = "";
    private String currVersionName = "";

    public MainPagePresenter(MainPageContract.View view,Context context) {
        this.mainView = view;
        this.context = context;
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
     * @desc 读取更新文件中的信息
     * @author wy
     * @date
     */
    private void readUpdateInfo(String updateInfoUrl) {
        //创建并启动获取更新信息的线程
        UpdateInfoThread updateInfoThread = new UpdateInfoThread(updateInfoUrl);
        updateInfoThread.start();
    }
}
