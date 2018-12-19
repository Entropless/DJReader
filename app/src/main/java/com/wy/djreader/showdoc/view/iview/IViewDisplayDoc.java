package com.wy.djreader.showdoc.view.iview;

import android.content.Context;

import com.dianju.showpdf.DJContentView;

/**
 * @ClassN IViewDisplayDoc
 * @desc DisplayDocActivity  UI逻辑
 * @author think
 * @date 2018/12/14 14:50
 */
public interface IViewDisplayDoc {
    //监听视图绘制
    void viewDrawListener(boolean isListener);
    //显示控制按钮
    void showFunctionBtn();

    void setBtnDown();

    void setBtnUp();
}
