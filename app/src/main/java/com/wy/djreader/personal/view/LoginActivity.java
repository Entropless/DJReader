package com.wy.djreader.personal.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import com.wy.djreader.R;

public class LoginActivity extends Activity {

	private WebView login_web_view;
	private String login_view_url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		login_web_view = this.findViewById(R.id.login_web_view);
		login_view_url = this.getString(R.string.login_view_url);
		login_web_view.loadUrl(login_view_url);
	}
}
