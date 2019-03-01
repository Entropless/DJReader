package com.wy.djreader.welcome.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wy.djreader.R;
import com.wy.djreader.welcome.countdown.CountDownView;

public class ViewPagerAdapter extends PagerAdapter {
	
	private Context context;
	private CountDownView count_down_view;
	private int[] adPics = {R.drawable.index,R.drawable.index, R.drawable.index, R.drawable.index,R.drawable.index};
	
	public ViewPagerAdapter(Context context) {
		super();
		this.context = context;
		this.count_down_view = count_down_view;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return adPics.length;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// 
		View view = View.inflate(context, R.layout.vp_item, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.vp_item);
		imageView.setBackgroundResource(adPics[position]);
		container.addView(view);
		return view;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return view == object;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}

}
