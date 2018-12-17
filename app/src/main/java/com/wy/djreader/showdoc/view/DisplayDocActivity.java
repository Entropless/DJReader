package com.wy.djreader.showdoc.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wy.djreader.R;
import com.wy.djreader.showdoc.presenter.DisplayDocPresenter;
import com.wy.djreader.showdoc.presenter.ipresenter.IPresenterDisplayDoc;
import com.wy.djreader.showdoc.view.iview.IViewDisplayDoc;

public class DisplayDocActivity extends AppCompatActivity implements IViewDisplayDoc {

    private IPresenterDisplayDoc iPresenterDisplayDoc;
    private String filePath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doc);
        iPresenterDisplayDoc = new DisplayDocPresenter();
        filePath = iPresenterDisplayDoc.getFilePath();
    }

    @Override
    public void viewDrawListener() {

    }
}
