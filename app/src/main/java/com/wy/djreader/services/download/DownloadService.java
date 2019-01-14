package com.wy.djreader.services.download;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.wy.djreader.utils.NotificationUtil;
import com.wy.djreader.utils.httputil.OkHttpImpl;
import com.wy.djreader.utils.httputil.OkHttpUtil;

public class DownloadService extends Service {
    private DownloadHandler downloadHandler = null;
    private String downloadUrl;

    private final class DownloadHandler extends Handler{
        public DownloadHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            //下载文件(同步下载)
            OkHttpUtil okHttpUtil = new OkHttpImpl();
            Object object = okHttpUtil.syncGet(downloadUrl,OkHttpUtil.ReturnType.FILE,null);
            stopSelf();
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
        //创建一个Handler线程，使用HandlerThread可以在Handler内执行异步任务
        HandlerThread downloadThread = new HandlerThread("downloadThread");
        downloadThread.start();
        //获取Looper，传入Handler
        Looper looper = downloadThread.getLooper();
        downloadHandler = new DownloadHandler(looper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取传递过来的数据
        downloadUrl = intent.getExtras().getString("apkUrl");
        Message msg = downloadHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        downloadHandler.sendMessage(msg);
        return START_REDELIVER_INTENT;//重启服务时传递最后一个intent
    }
}
