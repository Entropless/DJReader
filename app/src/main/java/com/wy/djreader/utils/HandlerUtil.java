package com.wy.djreader.utils;

import android.os.Handler;
import android.os.Message;

import com.dianju.showpdf.DJContentView;

public class HandlerUtil {
    public static Handler fileHandler = new FileHandler();

    static class FileHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DJContentView.DJCode.OPEN_FILE://打开文件
                    if (msg.arg1 == 1) {//加载成功
                        //打开文件后，记录传递过来的文件信息，当做最近打开的文件
//                        recordOpenedFiles(filePath);
                    }
                    break;
                case DJContentView.DJCode.SEAL://盖章返回
//                    cleanSelectExBtn();
                    break;
                default:
                    break;
            }
        }
    }
}
