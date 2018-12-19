package com.wy.djreader.utils;

import android.content.Context;

import com.dianju.showpdf.DJContentView;

/**
 * @ClassN SingleDJContentView
 * @desc 单例模式获取DJContentView实例
 * @author wy
 * @date 2018/12/19 17:20
 */
public class SingleDJContentView extends DJContentView{
    private static Context context;
    public SingleDJContentView(Context context) {
        super(context);
    }

    //静态内部类
    private static class Singleton{
        private static final SingleDJContentView contentView = new SingleDJContentView(context);
    }
    public static SingleDJContentView getInstance(Context context){
        SingleDJContentView.context = context;
        return Singleton.contentView;
    }
}
