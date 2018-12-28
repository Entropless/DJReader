package com.wy.djreader.utils.Singleton;

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
public class SingleDJContentView extends DJContentView{
    private static Context context;

    public SingleDJContentView(Context context) {
        super(context);
    }

    public SingleDJContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleDJContentView(Context context, boolean isEmptyPage, int wid, int height, int page) {
        super(context, isEmptyPage, wid, height, page);
    }

    public SingleDJContentView(Context context, boolean isEmptyPage, int wid, int height, int page, int alpha) {
        super(context, isEmptyPage, wid, height, page, alpha);
    }

    public SingleDJContentView(Context context, boolean isEmptyPage, int wid, int height, int page, int alpha, boolean isTransparentPen) {
        super(context, isEmptyPage, wid, height, page, alpha, isTransparentPen);
    }

    public SingleDJContentView(Context context, boolean isEmptyPage, int wid, int height, int page, boolean isSetbackGround, Bitmap backgroundbitmap) {
        super(context, isEmptyPage, wid, height, page, isSetbackGround, backgroundbitmap);
    }

    public SingleDJContentView(Context context, boolean isEmptyPage, int wid, int height, int page, boolean isSetbackGround, Bitmap backgroundbitmap, int alpha) {
        super(context, isEmptyPage, wid, height, page, isSetbackGround, backgroundbitmap, alpha);
    }


    //静态内部类
    private static class Singleton{
        private static final SingleDJContentView contentView = new SingleDJContentView(context);

//        private static final SingleDJContentView contentView_1 = new SingleDJContentView(context,  )
    }
    public static SingleDJContentView getInstance(Context context){
        SingleDJContentView.context = context;
        return Singleton.contentView;
    }
}
