package com.wy.djreader.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class FileOperation {

    /**
     * @desc 解析URI
     * @author wy
     * @date 2018/12/21 17:07
     * @return String
     */
    public String parseFileUri(Intent intent, Context context) {
        String filePath = "";
        Bundle extra = intent.getExtras();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW == action) {
            Uri uri = intent.getData();
            filePath = getUriAbsolutePath(context,uri);
        }else {
            filePath = extra.getString("filePath");
        }
        return filePath;
    }

    /**
     * @desc 根据URI信息获取文件绝对路径
     * @author wy
     * @date 2018/12/21 17:06
     * @return String
     */
    private String getUriAbsolutePath(Context context, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String absoluPath = null;
        if (scheme == null)
            absoluPath = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            absoluPath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        absoluPath = cursor.getString(index);
                    } else {
                        String tempPath = uri.getPath();
                        if (tempPath.startsWith("/root")) {
                            tempPath = tempPath.substring(5, tempPath.length());
                        }
                        absoluPath = tempPath;
                    }
                }
                cursor.close();
            }
        }
        return absoluPath;
    }
}
