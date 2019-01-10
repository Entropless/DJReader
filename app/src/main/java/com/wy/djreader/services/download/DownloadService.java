package com.wy.djreader.services.download;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

public class DownloadService extends Service {
    private DownloadHandler downloadHandler = null;

    private final class DownloadHandler extends Handler{
        public DownloadHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            //下载文件

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建一个Handler线程
        HandlerThread downloadThread = new HandlerThread("downloadThread");
        downloadThread.start();
        //获取Looper，传入Handler
        Looper looper = downloadThread.getLooper();
        downloadHandler = new DownloadHandler(looper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = downloadHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        downloadHandler.sendMessage(msg);
        return START_REDELIVER_INTENT;//重启服务时传递最后一个intent
    }
}
