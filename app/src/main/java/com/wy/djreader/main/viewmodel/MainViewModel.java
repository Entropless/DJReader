package com.wy.djreader.main.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.util.Log;

public class MainViewModel extends BaseObservable {
    //field
    //navigation btn
    private String fileMgBtn;
    private String functionBtn;
    private String personalBtn;
    private int downloadMax;
    private int progress;

    @Bindable
    public String getFileMgBtn() {
        return fileMgBtn;
    }

    public void setFileMgBtn(String fileMgBtn) {
        this.fileMgBtn = fileMgBtn;
    }

    @Bindable
    public String getFunctionBtn() {
        return functionBtn;
    }

    public void setFunctionBtn(String functionBtn) {
        this.functionBtn = functionBtn;
    }

    @Bindable
    public String getPersonalBtn() {
        return personalBtn;
    }

    public void setPersonalBtn(String personalBtn) {
        this.personalBtn = personalBtn;
    }

    @Bindable
    public int getDownloadMax() {
        Log.i("getmax",downloadMax+"");
        return downloadMax;
    }

    public void setDownloadMax(int downloadMax) {
        Log.i("setmax",downloadMax+"");
        this.downloadMax = downloadMax;
    }

    @Bindable
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public MainViewModel() {
    }

    public MainViewModel(int downloadMax, int progress) {
        this.downloadMax = downloadMax;
        this.progress = progress;
    }
}
