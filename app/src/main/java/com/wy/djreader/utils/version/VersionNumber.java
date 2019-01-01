package com.wy.djreader.utils.version;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class VersionNumber {
    public static String[] getVersionInfo(Context context) throws PackageManager.NameNotFoundException {
        String[] versionInfos = new String[2];
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        versionInfos[0] = String.valueOf(packageInfo.versionCode);
        versionInfos[1] = packageInfo.versionName;
        return versionInfos;
    }
}
