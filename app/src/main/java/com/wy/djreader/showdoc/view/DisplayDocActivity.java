package com.wy.djreader.showdoc.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

import com.dianju.showpdf.DJContentView;
import com.wy.djreader.R;
import com.wy.djreader.databinding.ActivityShowDocBinding;
import com.wy.djreader.showdoc.presenter.DisplayDocPresenter;
import com.wy.djreader.showdoc.presenter.ipresenter.IPresenterDisplayDoc;
import com.wy.djreader.showdoc.view.iview.IViewDisplayDoc;
import com.wy.djreader.utils.SingleDJContentView;

public class DisplayDocActivity extends AppCompatActivity implements IViewDisplayDoc {

    private IPresenterDisplayDoc iPresenterDisplayDoc;
    private String filePath = "";
    private ActivityShowDocBinding activityShowDocBinding = null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
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
                //获取实例
                DJContentView contentView = SingleDJContentView.getInstance(context);
                //
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
