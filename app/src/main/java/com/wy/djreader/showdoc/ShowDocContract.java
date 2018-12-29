package com.wy.djreader.showdoc;

import android.content.Context;

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
        //记录打开的文档信息
        void recordReadFilesInfos(Context context,String filePath);
        //获取文档缩略图
        byte[] getFileThumb(String filePath);
    }
}
