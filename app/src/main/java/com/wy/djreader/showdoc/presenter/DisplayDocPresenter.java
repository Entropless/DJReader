package com.wy.djreader.showdoc.presenter;

import com.dianju.showpdf.DJContentView;
import com.wy.djreader.showdoc.presenter.ipresenter.IPresenterDisplayDoc;
import com.wy.djreader.showdoc.view.iview.IViewDisplayDoc;

public class DisplayDocPresenter implements IPresenterDisplayDoc {

    private IViewDisplayDoc iViewDisplayDoc;
    private DJContentView contentView;
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

    }
}
