package com.wy.djreader.personal.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.wy.djreader.R;


@SuppressLint("SetJavaScriptEnabled")
public class HelpActivity extends Activity {

	private WebView help_page;
	private ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_help);
//		help_page = new WebView(this);
		help_page = (WebView) this.findViewById(R.id.help_page);
		back = (ImageView) this.findViewById(R.id.back_help);
		WebSettings webSettings = help_page.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setUseWideViewPort(true);//将图片调整到适合webView的大小
		webSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
//		try {
			help_page.loadUrl(this.getString(R.string.help_page_url));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		setContentView(help_page);
		
		//返回
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
}
