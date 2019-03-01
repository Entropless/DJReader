package com.wy.djreader.welcome.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wy.djreader.R;


/**
* @ClassN 
* @Desc 自定义ViewPager类(前后各加一张图片，无限滑动无卡顿)
* @author wy  
* @date 2018年7月5日
 */
public class AutoScrollViewPager<T extends PagerAdapter> extends FrameLayout {

	private Context context;
	private ViewPager viewPager;
	private PagerAdapter pageAdapter;
	private int oldPosition = 0;//上一个位置
	private int currentIndex = 1;//当前位置
	private boolean autoPlay = false;//自动滑动
	private int intervalTime = 2000;//自动滑动间隔时间
	private LinearLayout mlinearLayout;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			play();
		};
	};
	private Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(++currentIndex);
		}
	};
	public AutoScrollViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public AutoScrollViewPager(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public AutoScrollViewPager(Context context) {
		this(context,null);
	}

	private void init() {
		// TODO Auto-generated method stub
		context = getContext();
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);//启用硬件优化
		setClipChildren(false);//是否限制子view在其区域内
		viewPager = new ViewPager(context);
		addView(viewPager);
		//小圆点布局
		mlinearLayout = new LinearLayout(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM;
		mlinearLayout.setGravity(Gravity.CENTER);
		mlinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		addView(mlinearLayout,params);
	}
	
	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		play();//
	}
	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		cancle();
	}
	
	//初始化ViewPager
	private void initViewPager(){
		if (pageAdapter == null) {
			return;
		}
		viewPager.setCurrentItem(currentIndex);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//新页面将要被确定为下来是调用，position新页面index
				// 无限循环
				if (position == 0) {
				    currentIndex = pageAdapter.getCount() - 2;
				}else if (position == pageAdapter.getCount() -1) {
					currentIndex = 1;
				}else {
					currentIndex = position;
				}
				//小圆点currentIndex从1开始
				mlinearLayout.getChildAt(oldPosition).setEnabled(false);//上一个圆点取消选中
				mlinearLayout.getChildAt(currentIndex - 1).setEnabled(true);//当前圆点置为选中
				oldPosition = currentIndex - 1;
				Log.i("wy", "position:"+position+"currentIndex:"+currentIndex);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// 页面正在滑动时调用，当前位置，偏移百分比，偏移像素位置
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// 滑动状态改变时调用
				//1.SCROLL_STATE_DRAGGING是指当前页面正在被用户拖动 2.SCROLL_STATE_SETTLING则指用户的滑动动作已经结束,可以确认新页面了
				//3.SCROLL_STATE_IDLE则指当前页面处于空闲稳定的状态
				if (state == ViewPager.SCROLL_STATE_IDLE) {
					viewPager.setCurrentItem(currentIndex,false);
					play();//页面稳定时启动自动滑动
				}else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
					cancle();//手动滑动过程中取消自动滑动
				}
			}
		});
		loadIndicatorDot();
		mlinearLayout.getChildAt(0).setEnabled(true);
	}

	private void cancle() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(runnable);
	}

	private void play() {
		// TODO Auto-generated method stub
		if (autoPlay) {
			handler.postDelayed(runnable, intervalTime);
		}else {
			handler.removeCallbacks(runnable);
		}
	}
	/**
	 * 加载底部小圆点
	 */
	private void loadIndicatorDot(){
		for (int i = 0; i < pageAdapter.getCount() - 2; i++) {
			View v = new View(context);
			v.setBackgroundResource(R.drawable.point_selector);
			v.setEnabled(false);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
			if (i != 0) {
				params.leftMargin = 20;
			}
			params.bottomMargin = 20;
			v.setLayoutParams(params);
			mlinearLayout.addView(v);
		}
	}
	/**
	 * 设置适配器
	 */
	public void setAdapter(T adapter){
		pageAdapter = adapter;
		viewPager.setAdapter(pageAdapter);
		initViewPager();
	}
	/**
	 * 设置是否自动播放
	 */
	public void setAutoPlay(boolean autoPlay){
		this.autoPlay = autoPlay;
		if (!autoPlay) {
			handler.removeCallbacks(runnable);
		}
	}
}
