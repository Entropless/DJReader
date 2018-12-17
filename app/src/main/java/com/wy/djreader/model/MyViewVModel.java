package com.wy.djreader.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.wy.djreader.model.entity.HaveReadFilesParcelable;
import com.wy.djreader.model.entity.HaveReadFilesSerializable;

public class MyViewVModel extends ViewModel {
    private HaveReadFilesParcelable files;
    MutableLiveData<HaveReadFilesSerializable> selected = new MutableLiveData<HaveReadFilesSerializable>();
    public void select(HaveReadFilesSerializable files){
        selected.setValue(files);
    }

    public LiveData<HaveReadFilesSerializable> getSelected(){
        return selected;
    }
}
