package com.wy.djreader.showdoc.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

import com.dianju.showpdf.DJContentView;
import com.wy.djreader.R;
import com.wy.djreader.base.BaseActivity;
import com.wy.djreader.base.BasePresenter;
import com.wy.djreader.databinding.ActivityShowDocBinding;
import com.wy.djreader.showdoc.ShowDocContact;
import com.wy.djreader.showdoc.presenter.DisplayDocPresenter;
import com.wy.djreader.utils.Constant;
import com.wy.djreader.utils.fileutil.FileOperation;
import com.wy.djreader.utils.permission.PermissionUtil;
import com.wy.djreader.utils.permission.PermissionUtilImpl;
import com.wy.djreader.utils.singleton.SingleDJContentView;
import com.wy.djreader.utils.ToastUtil;

import java.lang.ref.WeakReference;

public class DisplayDocActivity extends BaseActivity implements ShowDocContact.View {

    private ShowDocContact.Presenter presenter;
    private FileOperation fileOperation;
    private String filePath = "";
    private ActivityShowDocBinding docBinding = null;
    private Context context;
    private boolean isListener = true;
    private WeakReference<DisplayDocActivity> activityWeak;//弱引用
    private SingleDJContentView singleDJContentView;

    private Handler fileHandler = null;

    static class FileHandler extends Handler{
        private DisplayDocActivity activity;
        private String filePath;
        public FileHandler(WeakReference<DisplayDocActivity> activityWeak,String filePath) {
            activity = activityWeak.get();
            this.filePath = filePath;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (activity == null) return;
            switch(msg.what){
                case DJContentView.DJCode.OPEN_FILE://文件成功打开
                    //记录打开的文件信息
                    activity.presenter.recordReadFilesInfos(filePath);
                    break;

            }
        }
    }

    @Override
    protected int getLayoutId() {
        context = this;
        return R.layout.activity_show_doc;
    }

    @Override
    protected void initDataBinding(ViewDataBinding dataBinding) {
        docBinding = (ActivityShowDocBinding) dataBinding;
    }

    @Override
    protected void initialize() {
        //权限检查
        String[] permissions = new String[]{
                Constant.PermissionConstant.READ_EXTERNAL_STORAGE,
                Constant.PermissionConstant.WRITE_EXTERNAL_STORAGE};
        PermissionUtil permissionUtil = new PermissionUtilImpl(permissions,context);
        if (!permissionUtil.checkPermission()){
            permissionUtil.requestPermissions(Constant.PermissionConstant.REQUEST_CODE_2);
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
            case Constant.PermissionConstant.REQUEST_CODE_2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //权限申请成功
                    initDocument();
                } else {
                    //权限申请失败，提醒用户
                    ToastUtil.toastMessage(context,getString(R.string.storage_decline),ToastUtil.LONG);
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
        //初始化activity的若引用
        activityWeak = new WeakReference<>(this);
        fileHandler = new FileHandler(activityWeak,filePath);

        docBinding.showdocLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (isListener){
                    isListener = false;
                    singleDJContentView = SingleDJContentView.getInstance(context);
                    docBinding.showdocLayout.addView(singleDJContentView.djContentView);
                    singleDJContentView.djContentView.setZOrderMediaOverlay(true);
                    presenter.loadingDoc(filePath,singleDJContentView.djContentView);
                    //设置Handler，接收各种返回值
                    singleDJContentView.djContentView.setMyhandler(fileHandler);
                }
                return true;
            }
        });
    }

    @Override
    protected BasePresenter initPresenter() {
        presenter = new DisplayDocPresenter(this,context);
        return presenter;
    }
    /**
     * @desc 用于传入Fragment时调用
     * @author wy
     * @date 2018/12/21 15:37
     */
    @Override
    public void setPresenter(ShowDocContact.Presenter presenter) {

    }

    @Override
    public void showFunctionBtn() {
        docBinding.fileOperationLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setBtnDown() {

    }

    @Override
    public void setBtnUp() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (singleDJContentView.djContentView != null){
            singleDJContentView.djContentView.saveFile("");
            singleDJContentView.djContentView.disposeResource();
            docBinding.showdocLayout.removeView(singleDJContentView.djContentView);
        }
    }
}
