package com.dianju_wy.showpdf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
    private Paint paint;
    private Path path;
    private float x,y;//点击的坐标

    public DJContentView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLACK);//设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//划线
        paint.setStrokeWidth(10);//设置线的宽度
        paint.setAntiAlias(true);//设置抗锯齿
        //设置初始坐标
        path =  new Path();
//        path.moveTo(0,100);
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
     * 初始化绘图需要的工具
     */
    private void initView(){
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        //配置绘制参数
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        //开启绘制线程
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while (mIsDrawing){
            draw();
        }
    }
    private void draw(){
        try {
            canvas = surfaceHolder.lockCanvas();//获取canvas绘图工具
            canvas.drawColor(Color.WHITE);//绘制画布颜色
            canvas.drawPath(path,paint);
        }catch (Exception e){

        }finally {
            //释放canvas对象并提交画布
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
