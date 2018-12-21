package com.wy.djreader.showdoc.view;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.ViewTreeObserver;

import com.wy.djreader.R;
import com.wy.djreader.base_universal.BaseActivity;
import com.wy.djreader.databinding.ActivityShowDocBinding;
import com.wy.djreader.showdoc.ShowDocContract;
import com.wy.djreader.showdoc.presenter.DisplayDocPresenter;
import com.wy.djreader.utils.FileOperation;
import com.wy.djreader.utils.SingleDJContentView;

public class DisplayDocActivity extends BaseActivity implements ShowDocContract.View {

    private ShowDocContract.Presenter presenter;
    private FileOperation fileOperation;
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
        fileOperation = new FileOperation();
        filePath = fileOperation.parseFileUri(this.getIntent(),context);
        activityShowDocBinding.showdocLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isListener){
                    isListener = false;
                    SingleDJContentView contentView = SingleDJContentView.getInstance(context);
                    activityShowDocBinding.showdocLayout.addView(contentView);
                    presenter.loadingDoc(filePath,contentView);
                }
                return true;
            }
        });
    }

    @Override
    protected void initPresenter() {
        presenter = new DisplayDocPresenter(this);
    }
    /**
     * @desc 用于传入Fragment时调用
     * @author wy
     * @date 2018/12/21 15:37
     */
    @Override
    public void setPresenter(ShowDocContract.Presenter presenter) {

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
