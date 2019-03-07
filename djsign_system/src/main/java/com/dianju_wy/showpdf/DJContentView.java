package com.dianju_wy.showpdf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dianju_wy.showpdf.util.PageInfo;
import com.dianju_wy.showpdf.util.PageMode;
import com.dianju_wy.showpdf.util.ShowPageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassN DJContentView
 * @desc 绘图类
 * @author wang
 * @date 2019/2/28 17:36
 */
public class DJContentView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private DJNativeInterface djNative;
    private Context context;
    private SurfaceHolder surfaceHolder;//surface的持有者
    private Canvas canvas;//绘图的画布
    private Bitmap bitmap;
    private int pageNums;
    private boolean viewEnd;
    private int currPageNum;
    private List<PageInfo> pageInfos;//页面信息
    private float scale;//缩放比例
    private float minZoom;//最小缩放比例
    private float pageOriWMax;//文档中最宽的页
    private int screenW,screenH;//SurfaceView的宽高
    private float totalHeight;//文档总高度
    private int pageSplit = 20;// 页与页之间分割线的宽度
    private List<ShowPageInfo> showPages;//即将要显示的页的信息
    private PageMode pageMode = PageMode.MultiPage;//默认是连续页模式
    private float temphastotalHeight;//当前显示的页的总高度
    private float initShowHight;//文档打开后初始高度
    private float temphasHeight;//当前显示的页展示的高度

    private boolean mIsDrawing;//控制绘图线程的标志位
    private Paint paint;
    private Path path;
    private float x,y;//点击的坐标

    public DJContentView(Context context) {
        super(context);
//        paint = new Paint();
//        paint.setColor(Color.BLACK);//设置画笔颜色
//        paint.setStyle(Paint.Style.STROKE);//划线
//        paint.setStrokeWidth(10);//设置线的宽度
//        paint.setAntiAlias(true);//设置抗锯齿
//        //设置初始坐标
//        path =  new Path();
////        path.moveTo(0,100);
        initView();
    }

    public DJContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
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
     * 初始化绘图参数
     */
    private void initView(){
        //初始化JNI
        djNative = new DJNativeInterface();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        //配置绘制参数
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        mIsDrawing = true;
//        //开启绘制线程
//        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        viewEnd = true;
        screenW = width;
        screenH = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;

    }

    public int openFile(String path){
        this.bitmap = null;
        int openRes = djNative.openFile(path);
        pageNums = djNative.pageNums;
        //TODO 设置设备可用内存

        if (openRes > 0){
            //启动打开文档线程
            new Thread(new openThread()).start();
        }
        return openRes;
    }

    /**
     *
     */
    class openThread implements Runnable{
        @Override
        public void run() {
            currPageNum = 0;
            try {
                while(!viewEnd){
                    Thread.sleep(300);
                }
                initOpen();

            }catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 初始化打开文档的数据
     */
    private void initOpen() {
        pageOriWMax = 0;
        if (djNative.objID < 1){
            drawText("文档打开失败",surfaceHolder);
            return;
        }
        if (pageInfos == null){
            pageInfos = new ArrayList<>();
        }else {
            pageInfos.clear();
        }
        for (int i = 0; i < pageNums; i++) {
            djNative.gotoPage(i);
            PageInfo pageInfo = new PageInfo();
            pageInfo.pageOriW = djNative.getPageWidth(i);
            pageInfo.pageOriH = djNative.getPageHeight(i);
            pageInfos.add(pageInfo);
            if(pageOriWMax<pageInfo.pageOriW){
                pageOriWMax=pageInfo.pageOriW;
            }
        }
        scale = screenW/pageOriWMax;//计算缩放比例
        djNative.setAndroidPageInfo(djNative.objID,scale,scale,0,0,screenW,screenH);
        djNative.setValue(djNative.objID,"SET_CURRPDF_PAGE", "0");
        minZoom = scale;
        computeTotalHeight();
        if (showPages == null){
            showPages = new ArrayList<>();
        }else {
            showPages.clear();
        }
        if (pageMode == PageMode.MultiPage){
            temphastotalHeight = 0;
            temphasHeight = 0;
            if (totalHeight < screenH){

            }else {

            }
        }
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



    /**
     * 画文字
     * @param text
     * @param holder
     */
    private void drawText(String text,SurfaceHolder holder){
        Canvas canvas = null;
        canvas=holder.lockCanvas();
        synchronized (holder) {
            if(canvas == null) return;
            canvas.drawColor(Color.WHITE);
            if(text!=null&&!text.equals("")){
                Paint p = new Paint();
                p.setTextAlign(Paint.Align.CENTER);
                p.setAlpha(100);
                p.setTextSize(60.0F);
                float x = canvas.getWidth() / 2;
                float y = canvas.getHeight() / 2;
                canvas.drawText(text, x, y, p);
            }
        }
        holder.unlockCanvasAndPost(canvas);
    }

    /**
     * 计算文档缩放后的总高度(包含分隔线高度)
     * @date 2016年9月28日 下午5:43:57
     */
    private void computeTotalHeight(){
        totalHeight=0;
        for(int i=0;i<pageNums;i++){
            PageInfo info= pageInfos.get(i);
            info.pageW=info.pageOriW*scale;
            info.pageH=info.pageOriH*scale;
            totalHeight+=info.pageH;
        }
        totalHeight+=pageSplit*(pageNums-1);
    }

    private void computeShowPageInfo(float oriX, float oriY, float x,float y,int currPage){
        switch (pageMode){
            case MultiPage:
                ShowPageInfo showInfo = new ShowPageInfo();
                showInfo.pageIndex = currPage;
                showInfo.w = pageInfos.get(currPage).pageW < screenW ? pageInfos.get(currPage).pageW : screenW;
                showInfo.orix = oriX + showInfo.w < pageInfos.get(currPage).pageW ? oriX : pageInfos.get(currPage).pageW - showInfo.w;
                showInfo.oriy = oriY;
//                showInfo.h =
                break;
            case SinglePage:

                break;
        }
    }
}
