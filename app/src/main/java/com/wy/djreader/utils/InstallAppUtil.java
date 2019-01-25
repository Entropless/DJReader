package com.wy.djreader.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * app安装工具类
 */
public class InstallAppUtil {

    public static PendingIntent installApp(Context context,File file, boolean autoInstall){
        //创建Uri
        Uri uri;
        //创建安装apk的intent
        Intent apkIntent = new Intent();
        apkIntent.setAction(Intent.ACTION_VIEW);
        apkIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //设置临时读权限
            apkIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //使用Content URI
            uri = FileProvider.getUriForFile(context,"com.wy.djreader.fileprovider",file);
        }else {
            uri = Uri.fromFile(file);
        }
        apkIntent.setDataAndType(uri,"application/vnd.android.package-archive");
        if (autoInstall){
            context.startActivity(apkIntent);
            return null;
        }else {
            return PendingIntent.getActivity(context,Constant.PendingIntent.REQUESTCODE_2,apkIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

}
