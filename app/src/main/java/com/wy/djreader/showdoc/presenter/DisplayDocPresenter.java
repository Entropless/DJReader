package com.wy.djreader.showdoc.presenter;

import com.wy.djreader.showdoc.presenter.ipresenter.IPresenterDisplayDoc;

public class DisplayDocPresenter implements IPresenterDisplayDoc {
    @Override
    public String getFilePath() {
        return null;
    }

    @Override
    public int loadingDoc(String filePath) {
        return 0;
    }
}
