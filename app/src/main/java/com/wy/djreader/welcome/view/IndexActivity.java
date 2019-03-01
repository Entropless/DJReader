package com.wy.djreader.welcome.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wy.djreader.R;
import com.wy.djreader.main.view.MainActivity;
import com.wy.djreader.welcome.adapter.AutoScrollViewPager;
import com.wy.djreader.welcome.adapter.ViewPagerAdapter;
import com.wy.djreader.welcome.countdown.CountDownView;

import java.util.List;

public class IndexActivity extends Activity {

    private Context context;
    private ViewPager viewPager;
    private AutoScrollViewPager<PagerAdapter> autoViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private CountDownView count_down_view;
    private TextView descText;
    private LinearLayout dotsGroup;
    private LayoutInflater inflater;
    private int[] image_ids = { R.drawable.index, R.drawable.index, R.drawable.index, R.drawable.index,
            R.drawable.index };
    private List<ImageView> imageList;
    private List<ImageView> pointList;
    private boolean isAutoPlay = true;
    private int currentItem = 0;// 当前界面
	/*private ScheduledExecutorService scheduledExecutorService = null;

	 Handler handler = new Handler() {
	 public void handleMessage(android.os.Message msg) {
	 if (msg.what == 100) {
	 viewPager.setCurrentItem(currentItem);
	 System.out.println(currentItem);
	 }
	 };
	 };*/

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("oncreate","inonCreate");
        setContentView(R.layout.activity_index);
        context = this;
        initView();
        initViewPager();
        // initData();
        // if (isAutoPlay) {//开始自动轮播
        // startPlay();
        // }
//		 init();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","inonStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume","inonResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause","inonPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop","inonStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("onRestart","inonRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","inonDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("onSaveInstanceState","inonSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("onRestoreInstanceState","inonRestoreInstanceState");
    }

    //初始化
    private void initViewPager() {
        //
        mViewPagerAdapter = new ViewPagerAdapter(context);
        autoViewPager.setAdapter(mViewPagerAdapter);
        count_down_view = findViewById(R.id.countDownView);
        count_down_view.setCountDownTimerListener(new CountDownView.CountDownTimerListener() {

            @Override
            public void onStartCount() {
                // TODO Auto-generated method stub
                Log.i("wy", "倒计时开始");
                autoViewPager.setAutoPlay(true);
            }

            @Override
            public void onFinishCount() {
                // TODO Auto-generated method stub
                Log.i("wy", "倒计时结束");
                //跳转主界面
                intentToMain();
            }
        });
        count_down_view.onStart();
        count_down_view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 取消倒计时
                CountDownView.countDownTimer.cancel();
                intentToMain();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initView() {
        // TODO Auto-generated method stub
//		viewPager = (ViewPager) this.findViewById(R.id.view_pager);
        autoViewPager = (AutoScrollViewPager<PagerAdapter>) this.findViewById(R.id.auto_viewpager);
//		descText = (TextView) this.findViewById(R.id.ad_desc);
//		dotsGroup = (LinearLayout) this.findViewById(R.id.items_dot);
    }

    /**
     * 开始轮播图切换
     */
	/*private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 2, 2, TimeUnit.SECONDS);
		// 第一个参数是执行的任务，第二个参数初始化延迟，第三个参数是执行任务的周期；
	}

	*//**
     * 执行轮播图切换任务
     *//*
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && isAutoPlay) {
					scheduledExecutorService.shutdown();
					intentToMain();
				}
				currentItem = (currentItem + 1) % imageList.size();
				handler.sendEmptyMessage(100);
			}
		}
	}

	private void initData() {
		// TODO Auto-generated method stub
		imageList = new ArrayList<ImageView>();
		pointList = new ArrayList<ImageView>();
		for (int i = 0; i < image_ids.length; i++) {

			// 初始化图片资源
			ImageView imageView = new ImageView(context);
			imageView.setBackgroundResource(image_ids[i]);
			;
			imageList.add(imageView);
			// 初始化原点
			ImageView pointView = new ImageView(context);
			LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			layoutParams.leftMargin = 30;
			layoutParams.rightMargin = 30;
			if (i == 0) {
				pointView.setEnabled(true);
			} else {
				pointView.setEnabled(false);
			}
			pointView.setLayoutParams(layoutParams);
			pointView.setBackgroundResource(R.drawable.point_selector);
			pointList.add(pointView);
			dotsGroup.addView(pointView);
		}

		ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(imageList);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(currentItem);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private class MyOnPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 1:// 手势滑动，空闲中
				isAutoPlay = false;
				System.out.println(" 手势滑动，空闲中");
				break;
			case 2:// 界面切换中
				isAutoPlay = true;
				System.out.println(" 界面切换中");
				break;
			case 0:// 滑动结束，即切换完毕或者加载完毕
				// 当前为最后一张，此时从右向左滑，则切换到第一张
				if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
					viewPager.setCurrentItem(0);
					// scheduledExecutorService.shutdown();
					// intent();
					System.out.println(" 滑动到最后一张");
				}
				// 当前为第一张，此时从左向右滑，则切换到最后一张
				else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
					System.out.println(" 滑动到第一张");
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int pos) {
			// TODO Auto-generated method stub
			currentItem = pos;
			for (int i = 0; i < pointList.size(); i++) {
				if (i == pos) {
					pointList.get(pos).setEnabled(true);
				} else {
					pointList.get(i).setEnabled(false);
				}
			}
		}

	}

	void init() {
		// 5秒计时转入首页
		final Intent intent = new Intent(this, MainActivity.class);
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startActivity(intent);
				finish();
			}
		};
		timer.schedule(timerTask, 5000);
	}*/
    void intentToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
