package com.wy.djreader.showdoc.presenter.ipresenter;

import com.wy.djreader.utils.SingleDJContentView;

public interface IPresenterDisplayDoc {
    //获取需要打开的文件路径
    String getFilePath();
    //加载文件
    int loadingDoc(String filePath, SingleDJContentView contentView);
}
