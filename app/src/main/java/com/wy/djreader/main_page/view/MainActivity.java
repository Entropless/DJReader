package com.wy.djreader.main_page.view;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.wy.djreader.R;
import com.wy.djreader.base_universal.BaseActivity;
import com.wy.djreader.databinding.ActivityMainBinding;
import com.wy.djreader.file_manager.view.DocFragment;
import com.wy.djreader.function_manager.view.Function_fragment;
import com.wy.djreader.main_page.MainPageContract;
import com.wy.djreader.main_page.presenter.MainPagePresenter;
import com.wy.djreader.personal.view.MeFragment;
import com.wy.djreader.utils.ActivityUtil;

public class MainActivity extends BaseActivity implements MainPageContract.View, DocFragment.docFragmentInteractionListener,Function_fragment.appFragmentInteractionListener,MeFragment.meFragmentInteractionListener {

    private Toolbar mToolbar;
    private DocFragment docFragment;
    private MeFragment meFragment;
    private Function_fragment function_fragment;
    private FragmentManager fragmentManager;
    private MainPageContract.Presenter mainPresenter;
    private ActivityMainBinding mainBinding = null;
    private Context context;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.fileMg:
                    ActivityUtil.replaceFragment(fragmentManager,docFragment,R.id.fragment_container);
                    break;
                case R.id.functionMg:
                    ActivityUtil.replaceFragment(fragmentManager,function_fragment,R.id.fragment_container);
                    break;
                case R.id.accountMg:
                    ActivityUtil.replaceFragment(fragmentManager,meFragment,R.id.fragment_container);
                    break;
            }
            return true;//将选中项目显示为选中
        }
    };

    @Override
    protected int getLayoutId() {
        context = this;
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        mainPresenter = new MainPagePresenter(this,context);
    }

    @Override
    protected void initDataBinding(ViewDataBinding dataBinding) {
        mainBinding = (ActivityMainBinding) dataBinding;
    }

    @Override
    protected void initialize() {
        //检查APP更新
        mainPresenter.checkVersionUpdate();

        docFragment = DocFragment.newInstance("","");
        function_fragment = Function_fragment.newInstance("","");
        meFragment = MeFragment.newInstance("","");
        fragmentManager = getSupportFragmentManager();
        ActivityUtil.addFragmentToActivity(fragmentManager,docFragment,R.id.fragment_container);
        //BottomNavigationView点击事件
        mainBinding.bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    /**
     *
     * @param uri
     */
    @Override
    public void docFragmentInteraction(Uri uri) {

    }

    @Override
    public void meFragmentInteraction(Uri uri) {

    }


    @Override
    public void appFragmentInteraction(Uri uri) {

    }

    /**
     * @desc 为Fragment设置Presenter
     * @author wy
     * @date 2018/12/24 17:59
     * @params
     * @return 
     */
    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public void showUpdateDialog() {

    }
}
