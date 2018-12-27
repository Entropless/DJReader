package com.wy.djreader.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ActivityUtil {
    /**
     * @desc 添加fragment到Activity中
     * @author wy
     * @date 2018/12/27 14:22
     * @params
     * @return
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, @NonNull int framId){
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(framId,fragment);
            transaction.commit();
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * @desc 
     * @author wy
     * @date 2018/12/27 14:41
     * @params
     * @return 
     */
    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment,int framId){
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(framId,fragment);
            transaction.commit();
        } else {
            throw new NullPointerException();
        }
    }

}
