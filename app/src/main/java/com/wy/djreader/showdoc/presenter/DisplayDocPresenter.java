package com.wy.djreader.showdoc.presenter;

import android.content.Context;

import com.dianju.showpdf.DJContentView;
import com.wy.djreader.model.entity.SingleDJContentView;
import com.wy.djreader.showdoc.presenter.ipresenter.IPresenterDisplayDoc;
import com.wy.djreader.showdoc.view.iview.IViewDisplayDoc;

public class DisplayDocPresenter implements IPresenterDisplayDoc {

    private IViewDisplayDoc iViewDisplayDoc;
    private Context context;
    public DisplayDocPresenter(IViewDisplayDoc iViewDisplayDoc) {
        this.iViewDisplayDoc = iViewDisplayDoc;
    }

    @Override
    public String getFilePath() {
        return null;
    }

    @Override
    public int loadingDoc(String filePath) {
        iViewDisplayDoc.viewDrawListener(true);
        return 0;
    }

    @Override
    public void initDJContentView() {
        //获取DJContentView实例
//        SingleDJContentView.getInstance();
    }
}
