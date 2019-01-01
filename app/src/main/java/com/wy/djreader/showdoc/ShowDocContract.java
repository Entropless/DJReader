package com.wy.djreader.showdoc;

import com.dianju.showpdf.DJContentView;
import com.wy.djreader.base_universal.BasePresenter;
import com.wy.djreader.base_universal.BaseView;
import com.wy.djreader.utils.singleton.SingleDJContentView;

public interface ShowDocContract {

    interface View extends BaseView<Presenter>{
        //显示控制按钮
        void showFunctionBtn();

        void setBtnDown();

        void setBtnUp();
    }

    interface Presenter extends BasePresenter{
        //加载文件
        int loadingDoc(String filePath, DJContentView contentView);
        //记录打开的文档信息
        void recordReadFilesInfos(String filePath);
    }
}
