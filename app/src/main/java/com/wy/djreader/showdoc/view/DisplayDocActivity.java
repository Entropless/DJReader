package com.wy.djreader.showdoc.view;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.ViewTreeObserver;

import com.wy.djreader.R;
import com.wy.djreader.base_universal.BaseActivity;
import com.wy.djreader.databinding.ActivityShowDocBinding;
import com.wy.djreader.showdoc.presenter.DisplayDocPresenter;
import com.wy.djreader.showdoc.presenter.ipresenter.IPresenterDisplayDoc;
import com.wy.djreader.showdoc.view.iview.IViewDisplayDoc;
import com.wy.djreader.utils.SingleDJContentView;

public class DisplayDocActivity extends BaseActivity implements IViewDisplayDoc {

    private IPresenterDisplayDoc iPresenterDisplayDoc;
    private String filePath = "";
    private ActivityShowDocBinding activityShowDocBinding = null;
    private Context context;
    private boolean isListener = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_doc;
    }

    @Override
    protected void initDataBinding(ViewDataBinding dataBinding) {
        context = this;
        activityShowDocBinding = (ActivityShowDocBinding) dataBinding;
        filePath = iPresenterDisplayDoc.getFilePath();
        activityShowDocBinding.showdocLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isListener){
                    isListener = false;
                    SingleDJContentView contentView = SingleDJContentView.getInstance(context);
                    activityShowDocBinding.showdocLayout.addView(contentView);
                    iPresenterDisplayDoc.loadingDoc(filePath,contentView);
                }
                return true;
            }
        });
    }

    @Override
    protected void initPresenter() {
        iPresenterDisplayDoc = new DisplayDocPresenter(this);
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
