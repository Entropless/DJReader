package com.wy.djreader.document.presenter.ipresenter;

import android.content.Context;
import android.view.View;


public interface IDocPresenter {
    //从数据库获取文件信息
    void getFileListInfo(Context context);
    //双窗口判断
    boolean dualPuneJudge(Context context, View docFragment);
    //展示文档
    void display(int index);
}
