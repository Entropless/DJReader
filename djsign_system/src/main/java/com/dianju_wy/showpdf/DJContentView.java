package com.dianju_wy.showpdf;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DJContentView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private boolean mIsDrawing;

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

    /**
     * 初始化
     */
    private void initView(){
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
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

    @Override
    public void run() {

    }
}
