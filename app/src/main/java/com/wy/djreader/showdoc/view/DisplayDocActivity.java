package com.wy.djreader.showdoc.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.wy.djreader.R;
import com.wy.djreader.databinding.ActivityShowDocBinding;
import com.wy.djreader.showdoc.presenter.DisplayDocPresenter;
import com.wy.djreader.showdoc.presenter.ipresenter.IPresenterDisplayDoc;
import com.wy.djreader.showdoc.view.iview.IViewDisplayDoc;

public class DisplayDocActivity extends AppCompatActivity implements IViewDisplayDoc {

    private IPresenterDisplayDoc iPresenterDisplayDoc;
    private String filePath = "";
    private ActivityShowDocBinding activityShowDocBinding = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_doc);
        //databinding
        activityShowDocBinding = DataBindingUtil.setContentView(this,R.layout.activity_show_doc);
        //获取文件路径并加载文档
        iPresenterDisplayDoc = new DisplayDocPresenter(this);
        filePath = iPresenterDisplayDoc.getFilePath();
        iPresenterDisplayDoc.loadingDoc(filePath);
    }

    @Override
    public void viewDrawListener(final boolean isListener) {
        activityShowDocBinding.showdocLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                iPresenterDisplayDoc.initDJContentView();
                return true;
            }
        });
    }

    @Override
    public void showFunctionBtn() {

    }

    @Override
    public void setBtnDown() {

    }

    @Override
    public void setBtnUp() {

    }
}
