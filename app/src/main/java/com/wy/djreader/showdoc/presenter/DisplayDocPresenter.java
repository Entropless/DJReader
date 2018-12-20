package com.wy.djreader.showdoc.presenter;

import android.content.Context;

import com.dianju.showpdf.DJContentView;
import com.dianju.showpdf.ShowPageInfo;
import com.wy.djreader.showdoc.presenter.ipresenter.IPresenterDisplayDoc;
import com.wy.djreader.showdoc.view.iview.IViewDisplayDoc;
import com.wy.djreader.utils.SingleDJContentView;

public class DisplayDocPresenter implements IPresenterDisplayDoc {

    private IViewDisplayDoc iViewDisplayDoc;
    private Context context;
    private int openRes = -1;
    public DisplayDocPresenter(IViewDisplayDoc iViewDisplayDoc) {
        this.iViewDisplayDoc = iViewDisplayDoc;
    }

    @Override
    public String getFilePath() {
        return null;
    }

    @Override
    public int loadingDoc(String filePath, SingleDJContentView contentView) {
        if (filePath.endsWith(".pdf")){
            openRes = contentView.openFile(filePath);
        }else if (filePath.endsWith(".aip")){
            openRes = contentView.openTempFile(filePath);
        }
        //登录

        return openRes;
    }
}
