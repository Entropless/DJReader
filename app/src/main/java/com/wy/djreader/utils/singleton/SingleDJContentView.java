package com.wy.djreader.utils.singleton;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.dianju.showpdf.DJContentView;

/**
 * @ClassN SingleDJContentView
 * @desc 单例模式获取DJContentView实例
 * @author wy
 * @date 2018/12/19 17:20
 */
public class SingleDJContentView{
    public DJContentView djContentView = null;
    private static Context context;

    private SingleDJContentView() {
        this.djContentView = new DJContentView(context);
    }

    //静态内部类
    private static class Singleton{
        private static final SingleDJContentView singleContentView = new SingleDJContentView();
    }
    public static SingleDJContentView getInstance(Context context){
        SingleDJContentView.context = context;
        return Singleton.singleContentView;
    }
}
