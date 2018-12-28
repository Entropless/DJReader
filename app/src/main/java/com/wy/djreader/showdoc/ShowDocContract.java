package com.wy.djreader.showdoc;

import com.wy.djreader.base_universal.BasePresenter;
import com.wy.djreader.base_universal.BaseView;
import com.wy.djreader.utils.Singleton.SingleDJContentView;

public interface ShowDocContract {

    public interface View extends BaseView<Presenter>{
        //显示控制按钮
        void showFunctionBtn();

        void setBtnDown();

        void setBtnUp();
    }

    public interface Presenter extends BasePresenter{
        //加载文件
        int loadingDoc(String filePath, SingleDJContentView contentView);
    }
}
