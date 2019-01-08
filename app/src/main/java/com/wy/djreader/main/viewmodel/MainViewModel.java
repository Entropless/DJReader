package com.wy.djreader.main.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

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
        return downloadMax;
    }

    public void setDownloadMax(int downloadMax) {
        this.downloadMax = downloadMax;
    }

    @Bindable
    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
