package com.dianju_wy.showpdf;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @ClassN DJContentView
 * @desc 绘图类
 * @author wang
 * @date 2019/2/28 17:36
 */
public class DJContentView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder surfaceHolder;//surface的持有者
    private Canvas canvas;//绘图的画布
    private boolean mIsDrawing;//控制绘图线程的标志位

    public DJContentView(Context context) {
        super(context);
        initView();
    }

    public DJContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DJContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public DJContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * 初始化绘图需要的工具
     */
    private void initView(){
        surfaceHolder = getHolder();
        canvas = surfaceHolder.lockCanvas();

    }

    @Override
    public void run() {

    }
}
