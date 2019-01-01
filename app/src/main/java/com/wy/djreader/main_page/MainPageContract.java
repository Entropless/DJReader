package com.wy.djreader.main_page;

import com.wy.djreader.base_universal.BasePresenter;
import com.wy.djreader.base_universal.BaseView;

public interface MainPageContract {
    interface View extends BaseView {
        //显示更新提示
        void showUpdateDialog();
    }

    interface Presenter extends BasePresenter{
        //检查版本更新
        void checkVersionUpdate();

    }
}
