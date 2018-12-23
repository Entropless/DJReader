package com.wy.djreader.base_universal;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    //Activity的通用方法
    protected ViewDataBinding dataBinding;//数据绑定
    protected abstract int getLayoutId();//获取布局ID
    protected abstract void initPresenter();//初始化presenter
    protected abstract void initDataBinding(ViewDataBinding dataBinding);//初始化数据绑定
    protected abstract void initialize();//初始化Activity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,getLayoutId());
        initPresenter();
        initDataBinding(dataBinding);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
