package com.wy.djreader.main_page.view;

import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.wy.djreader.R;
import com.wy.djreader.base_universal.BaseActivity;
import com.wy.djreader.file_manager.view.DocFragment;
import com.wy.djreader.function_manager.view.Function_fragment;
import com.wy.djreader.main_page.MainPageContract;
import com.wy.djreader.main_page.presenter.MainPagePresenter;
import com.wy.djreader.personal.view.MeFragment;

public class MainActivity extends BaseActivity implements MainPageContract.View, DocFragment.docFragmentInteractionListener,Function_fragment.appFragmentInteractionListener,MeFragment.meFragmentInteractionListener {

    private Toolbar mToolbar;
    private DocFragment docFragment;
    private MeFragment meFragment;
    private Function_fragment function_fragment;
    private ImageView docItem,appItem,meItem;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private MainPageContract.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        presenter = new MainPagePresenter(this);
    }

    @Override
    protected void initDataBinding(ViewDataBinding dataBinding) {
        
    }

    @Override
    protected void initialize() {

    }

    private void initView() {
        //        mToolbar = this.findViewById(R.id.toolbar);
        docItem = this.findViewById(R.id.doc_item);
        appItem = this.findViewById(R.id.app_item);
        meItem = this.findViewById(R.id.me_item);
    }
    private void init(){
        //        setSupportActionBar(mToolbar);
        docFragment = DocFragment.newInstance("","");
        function_fragment = Function_fragment.newInstance("","");
        meFragment = MeFragment.newInstance("","");
        //get instance of fragmentTransaction
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        //执行插入事务
        ft.add(R.id.fragment_container,docFragment);
        ft.commit();
        //图片点击事件
        docItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,docFragment);
//                ft.addToBackStack(null);
                ft.commit();
            }
        });
        appItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, function_fragment);
//                ft.addToBackStack(null);
                ft.commit();
            }
        });
        meItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,meFragment);
//                ft.addToBackStack(null);
                ft.commit();
            }
        });
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
}
