package com.wy.djreader.base;

public interface BasePresenter {

    //页面销毁时通知presenter将view = null
    void onDestroy();
}
