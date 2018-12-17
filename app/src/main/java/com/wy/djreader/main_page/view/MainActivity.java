package com.wy.djreader.main_page.view;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.wy.djreader.R;
import com.wy.djreader.function_manager.view.function_fragment;
import com.wy.djreader.file_manager.view.DocFragment;
import com.wy.djreader.personal.view.MeFragment;

public class MainActivity extends AppCompatActivity implements DocFragment.docFragmentInteractionListener,function_fragment.appFragmentInteractionListener,MeFragment.meFragmentInteractionListener {

    private Toolbar mToolbar;
    private DocFragment docFragment;
    private MeFragment meFragment;
    private function_fragment functionfragment;
    private ImageView docItem,appItem,meItem;
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
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
        functionfragment = function_fragment.newInstance("","");
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
                ft.replace(R.id.fragment_container, functionfragment);
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
}
