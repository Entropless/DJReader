package com.wy.djreader.utils.Permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtilImpl implements PermissionUtil{

    private Context context;
    String[] permissions = null;
    List<String> not_granted = new ArrayList<>();
    String[] not_granted_pms = null;

    public PermissionUtilImpl(@NonNull String[] permissions, Context context) {
        this.context = context;
        this.permissions = permissions;
    }

    @Override
    public boolean checkPermission() {
        for (String permission: permissions) {
            //使用支持库来检查权限，不需要检查系统版本
            int permissionCheck = ContextCompat.checkSelfPermission(context,permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                continue;
            } else {
                not_granted.add(permission);
                return false;
            }
        }
        return true;
    }

    @Override
    public String[] getUnauthorizedPms() {
        not_granted_pms = not_granted.toArray(new String[not_granted.size()]);
        return not_granted_pms;
    }

    @Override
    public void requestPermissions() {
        getUnauthorizedPms();
        ActivityCompat.requestPermissions((Activity) context,not_granted_pms,1);

    }
}
