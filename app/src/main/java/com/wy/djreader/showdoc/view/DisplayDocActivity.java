package com.wy.djreader.showdoc.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.ViewTreeObserver;

import com.wy.djreader.R;
import com.wy.djreader.base_universal.BaseActivity;
import com.wy.djreader.databinding.ActivityShowDocBinding;
import com.wy.djreader.showdoc.ShowDocContract;
import com.wy.djreader.showdoc.presenter.DisplayDocPresenter;
import com.wy.djreader.utils.Constant;
import com.wy.djreader.utils.FileOperation;
import com.wy.djreader.utils.Permission.PermissionUtil;
import com.wy.djreader.utils.Permission.PermissionUtilImpl;
import com.wy.djreader.utils.SingleDJContentView;
import com.wy.djreader.utils.ToastUtil;

public class DisplayDocActivity extends BaseActivity implements ShowDocContract.View {

    private ShowDocContract.Presenter presenter;
    private FileOperation fileOperation;
    private String filePath = "";
    private ActivityShowDocBinding activityShowDocBinding = null;
    private Context context;
    private boolean isListener = true;
    private PermissionUtil permissionUtil = null;
    private String[] permissions = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_doc;
    }

    @Override
    protected void initDataBinding(ViewDataBinding dataBinding) {
        context = this;
        activityShowDocBinding = (ActivityShowDocBinding) dataBinding;
    }

    @Override
    protected void initialize() {
        //权限检查
        permissions = new String[]{
                Constant.PermissionConstant.READ_EXTERNAL_STORAGE,
                Constant.PermissionConstant.WRITE_EXTERNAL_STORAGE};
        permissionUtil = new PermissionUtilImpl(permissions,context);
        if (!permissionUtil.checkPermission()){
            permissionUtil.requestPermissions();
        } else {
            initDocument();
        }
    }

    /**
     * @desc 请求权限时，用户响应后的回调方法
     * @author wy
     * @date 2018/12/24 9:41
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //权限申请成功
                    initDocument();
                } else {
                    //权限申请失败，提醒用户
                    ToastUtil.toastMessage(context,getString(R.string.storage_decline),ToastUtil.LOGN);
                }
                break;
        }
    }

    /**
     * @desc 初始化文档
     * @author wy
     * @date 2018/12/24 13:58
     */
    private void initDocument(){
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
