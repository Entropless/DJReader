package com.wy.djreader.base_universal;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    //Activity的通用方法
    protected ViewDataBinding dataBinding;
    protected abstract int getLayoutId();
    protected abstract void initDataBinding(ViewDataBinding dataBinding);
    protected abstract void initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,getLayoutId());
        initPresenter();
        initDataBinding(dataBinding);
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
