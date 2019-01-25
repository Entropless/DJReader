package com.wy.djreader.utils;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.wy.djreader.R;

import java.io.File;

public class NotificationUtil {

    private static Context appContext;
    private static NotificationCompat.Builder mBuilder;

    /**
     * 初始化通知
     * @param context 上下文
     * @param activity 点击通知需要进入的activity
     * @param rank 通知的等级
     */
    public static void initNotification(Context context,Activity activity, File file, int rank){
        appContext = context;
        PendingIntent pendingIntent;
        //API26及以上需要设置channel(通道)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建通道
            createNotificationChannel(rank);
        }
        //实例化Notification.Builder，并设置三个必须属性
        mBuilder = new NotificationCompat.Builder(appContext, Constant.Notification.CHANNEL_ID_1)//Android7.1以下会忽略channelId
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(appContext.getString(R.string.notification_title))
                .setContentText(appContext.getString(R.string.notification_text))
                .setPriority(rank);//Android7.1以下使用
        if (activity != null){
            Intent intent = new Intent(appContext,activity.getClass());
            //创建TaskStackBuilder对象
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(appContext);
            taskStackBuilder.addParentStack(activity);
            taskStackBuilder.addNextIntent(intent);
            pendingIntent = taskStackBuilder.getPendingIntent(Constant.PendingIntent.REQUESTCODE_0,PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            pendingIntent = InstallAppUtil.installApp(appContext,file,false);
        }

        //为通知设置PendingIntent
        mBuilder.setContentIntent(pendingIntent);
    }

    /**
     * 异步任务中更新进度
     * @param notifyId
     * @param max
     * @param progress
     * @param indeterminate
     * @param finish 完成提示信息
     */
    public static boolean updateNotification(int notifyId, int max, int progress, boolean indeterminate, String finish) {
        boolean updateSuccessful = false;
        //获取NotificationManager实例
        NotificationManager notificationManager = (NotificationManager) appContext.getSystemService(Service.NOTIFICATION_SERVICE);
        //判断是否添加进度
        if (max == 0) {
            notificationManager.notify(notifyId,mBuilder.build());
            updateSuccessful = true;
        }else {
            if (progress == max) {
                mBuilder.setContentText(finish);
                mBuilder.setProgress(0,0,false);
                notificationManager.notify(notifyId,mBuilder.build());
                updateSuccessful = true;
            }else {
                mBuilder.setProgress(max,progress,indeterminate);
                notificationManager.notify(notifyId,mBuilder.build());
            }
        }
        return updateSuccessful;
    }

    /**
     * Create NotificationChannel(API26+)，NotificationChannel 不在支持库中
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createNotificationChannel(int importance) {
        CharSequence name = appContext.getString(R.string.channel_name);
        String description = appContext.getString(R.string.channel_description);
        //创建NotificationChannel实例
        NotificationChannel channel = new NotificationChannel(Constant.Notification.CHANNEL_ID_1,name,importance);
        channel.setDescription(description);
        //向系统注册Channel,注册之后，不能修改channelId或者其他通知行为
        NotificationManager notificationManager = appContext.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}
