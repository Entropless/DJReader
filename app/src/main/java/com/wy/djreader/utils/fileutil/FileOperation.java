package com.wy.djreader.utils.fileutil;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import com.wy.djreader.utils.Constant;
import com.wy.djreader.utils.MessageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class FileOperation {

    /**
     * @return String
     * @desc 解析URI
     * @author wy
     * @date 2018/12/21 17:07
     */
    public String parseFileUri(Intent intent, Context context) {
        String filePath = "";
        Bundle extra = intent.getExtras();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW == action) {
            Uri uri = intent.getData();
            filePath = getUriAbsolutePath(context, uri);
        } else {
            filePath = extra.getString("filePath");
        }
        return filePath;
    }

    /**
     * @return String
     * @desc 根据URI信息获取文件绝对路径
     * @author wy
     * @date 2018/12/21 17:06
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

    /**
     * 将输入流写入文件，耗时操作
     *
     * @param inputStream
     * @param contentLength
     * @param filePath
     * @param fileName
     * @param showProgress
     */
    public static boolean writeToFile(InputStream inputStream, long contentLength, String filePath, String fileName, boolean showProgress, Handler handler) {
        boolean writeSuccess = false;
        File folder = new File(filePath);
        File file;
        FileOutputStream fout;
        try {
            boolean hasFolder;
            if (!folder.exists()) {
                hasFolder = folder.mkdirs();
                if (!hasFolder) {
                    throw new RuntimeException("create folder is failed!");
                }
            }
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            long total = contentLength;
            int tot;
            if (total > Integer.MAX_VALUE) {
                throw new RuntimeException("contentLength is over Integer MaxValue");
            } else {
                tot = (int) total;
            }
            fout = new FileOutputStream(file);
            byte[] buffer = new byte[2048];
            int len;
            if (!showProgress) {
                while (-1 != (len = inputStream.read(buffer))) {
                    fout.write(buffer, 0, len);
                }
                if (file.length() == contentLength){
                    writeSuccess = true;
                }
            } else {
                long now = 0;//当前写入大小
                int progress;//进度
                int oldProgress = 0;//上次写入大小
                BigDecimal bigDecimal;
                while (-1 != (len = inputStream.read(buffer))) {
                    fout.write(buffer, 0, len);
                    now += len;
                    float percent = (float) now / total;
                    bigDecimal = new BigDecimal(percent);
                    percent = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN).floatValue();//只取小数点后两位
                    progress = (int) (percent * 100);
                    if ((progress - oldProgress) > 2 || now == total) {
                        oldProgress = progress;
                        Bundle data = new Bundle();
                        data.putInt("progress", progress);
                        data.putInt("total", tot);
                        if (now == total) {
                            writeSuccess = true;
                            MessageManager msg = new MessageManager(handler, Constant.Flag.DOWN_OK,data);
                            msg.sendMessage();
                        }else {
                            MessageManager msg = new MessageManager(handler, Constant.Flag.DOWN_ING, data);
                            msg.sendMessage();
                        }
                    }
                }
            }
            fout.flush();
            inputStream.close();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writeSuccess;
    }
}
