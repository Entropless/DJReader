package com.wy.djreader.main_page.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;

public class MainViewModel extends BaseObservable {
    //field
    //navigation btn
    private String fileMgBtn;
    private String functionBtn;
    private String personalBtn;

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

}
