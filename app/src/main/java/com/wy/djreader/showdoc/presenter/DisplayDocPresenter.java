package com.wy.djreader.showdoc.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.dianju.showpdf.DJContentView;
import com.wy.djreader.showdoc.ShowDocContract;
import com.wy.djreader.utils.SingleDJContentView;

public class DisplayDocPresenter implements ShowDocContract.Presenter {

    private ShowDocContract.View view;
    private Context context;
    private String filePath = "";
    private int openRes = -1;

    //Presenter构造方法，M V P三层的连接点
    public DisplayDocPresenter(ShowDocContract.View view) {
        this.view = view;
    }

    @Override
    public int loadingDoc(String filePath, SingleDJContentView contentView) {
        if (filePath.endsWith(".pdf")){
            openRes = contentView.openFile(filePath);
        }else if (filePath.endsWith(".aip")){
            openRes = contentView.openTempFile(filePath);
        }
        Log.d("wy_openRes",openRes+"");
        //登录

        return openRes;
    }
}
