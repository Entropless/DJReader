package com.wy.djreader.welcome.countdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.wy.djreader.R;
import com.wy.djreader.utils.Constant;

/**
* @ClassN CountDownView
* @Desc 自定义倒计时View
* @author wy  
* @date 2018年7月5日
 */
public class CountDownView extends View {

	private CountDownTimerListener cdListener;
	public static CountDownTimer countDownTimer;
	
    private float radian_progress = 0.0f;//转过的弧度(进度)
	private int backgroundColor;//背景颜色
    private float borderWidth;//进度条粗细
    private int borderColor;//进度条的颜色
    private String text;//文字内容
    private int textColor;// 文字颜色
    private float textSize;//文字大小
    private int textWidth;
    
    //绘图
    private Paint circlePaint;//画圆
    private TextPaint textPaint;//画字
    private Paint borderPaint;//画进度
    
    private StaticLayout staticLayout;//文本换行

    public CountDownView(Context context) {
		this(context, null);
	}

	public CountDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
		backgroundColor = ta.getColor(R.styleable.CountDownView_background_color, Constant.CountDown.BACKGROUND_COLOR);
		borderWidth = ta.getDimension(R.styleable.CountDownView_border_width, Constant.CountDown.BORDER_WIDTH);
		borderColor = ta.getColor(R.styleable.CountDownView_border_color, Constant.CountDown.BORDER_COLOR);
		text = ta.getString(R.styleable.CountDownView_text);
		if (text == null || text.equals("")) {
			text = context.getResources().getString(R.string.countdown_text);
		}
		textColor = ta.getColor(R.styleable.CountDownView_text_color, Constant.CountDown.TEXT_COLOR);
		textSize = ta.getDimension(R.styleable.CountDownView_text_size, Constant.CountDown.TEXT_SIZE);
		ta.recycle();
		init();
	}

	private void init() {
		// 初始化画笔
		/*Paint.Style.STROKE 只绘制图形轮廓（描边）
		Paint.Style.FILL 只绘制图形内容
		Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容*/
		circlePaint = new Paint();
		circlePaint.setAntiAlias(true);//消除锯齿
		circlePaint.setDither(true);//设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
		circlePaint.setColor(backgroundColor);
		circlePaint.setStyle(Paint.Style.FILL);
		
		textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setDither(true);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setStyle(Paint.Style.STROKE);
        
//        textWidth = (int) textPaint.measureText(text.substring(0, (text.length() + 1) / 2));//从中间换行
        textWidth = (int) textPaint.measureText(text);
        if (text.length() >2) {
        	textWidth = (int) textPaint.measureText(text.substring(0, (text.length() + 1) / 2));//从中间换行
		}
        staticLayout = new StaticLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1F, 0, false);
	}
	
	//重写此方法的目的是测量控件的实际大小，当width和height是wrap_content，我们需要测量控件的实际大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {//精确模式
            width = staticLayout.getWidth();
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            height = staticLayout.getHeight();
        }
        setMeasuredDimension(width, height);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	// 
    	super.onDraw(canvas);
    	int width = getMeasuredWidth();
    	int height = getMeasuredHeight();
    	int min = Math.min(width, height);
    	//画圆盘
    	canvas.drawCircle(width/2, height/2, min/2, circlePaint);
    	//画圆环
    	RectF rectF;
    	if (width > height) {
            rectF = new RectF(width / 2 - min / 2 + borderWidth / 2, 0 + borderWidth / 2, width / 2 + min / 2 - borderWidth / 2, height - borderWidth / 2);
        } else {
            rectF = new RectF(borderWidth / 2, height / 2 - min / 2 + borderWidth / 2, width - borderWidth / 2, height / 2 - borderWidth / 2 + min / 2);
        }
    	//画弧
    	canvas.drawArc(rectF, 0, radian_progress, false, borderPaint);
    	//画文字,drawText是以靠近文字左下角为原点开始画的
//    	canvas.drawText(text, width/2 - textWidth/2, height/2 + textSize/2, textPaint);
    	canvas.translate(width / 2, height / 2 - staticLayout.getHeight() / 2);
        staticLayout.draw(canvas);
    }

	//倒计时开始
    public void onStart(){
    	if (cdListener != null) {
			cdListener.onStartCount();
		}
    	countDownTimer = new CountDownTimer(Constant.CountDown.countDownTime,Constant
    			.CountDown.freshPadding) {
			
			@Override
			public void onTick(long millisUntilFinished) {//millisUntilFinished倒计时剩余时间
				//刷新view
				radian_progress = (Constant.CountDown.countDownTime - millisUntilFinished)/((float)Constant.CountDown.countDownTime/360);
				invalidate();
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				radian_progress = 360;
				invalidate();
				if (cdListener != null) {
					cdListener.onFinishCount();
				}
			}
		}.start();
    }
    
    
    public void setCountDownTimerListener(CountDownTimerListener cdListener){
    	this.cdListener = cdListener;
    }
    //接口
    public interface CountDownTimerListener{
    	
    	void onStartCount();
    	void onFinishCount();
    }
}
