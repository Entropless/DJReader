package com.wy.djreader.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.wy.djreader.R;

public class NotificationUtil {

    private static Context appContext;
    private static NotificationCompat.Builder mBuilder;

    /**
     * 在异步任务中调用
     * @param context
     * @param intent
     * @param activity
     * @param notifyId
     * @param max
     * @param progress
     * @param limit
     */
    public static void initNotification(Context context,Activity activity){
        appContext = context;
        //实例化Notification.Builder，并设置三个必须属性
        mBuilder = new NotificationCompat.Builder(appContext,"default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(appContext.getString(R.string.notification_title))
                .setContentText(appContext.getString(R.string.notification_text));
        Intent intent = new Intent(appContext,activity.getClass());
        //创建TaskStackBuilder对象
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(appContext);
        taskStackBuilder.addParentStack(activity);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        //为通知设置PendingIntent
        mBuilder.setContentIntent(pendingIntent);
    }

    /**
     * 异步任务中更新进度
     * @param notifyId
     * @param max
     * @param progress
     * @param limit
     */
    public static void updateNotification(int notifyId, int max, int progress, boolean indeterminate, String finish) {
        //获取NotificationManager实例
        NotificationManager notificationManager = (NotificationManager) appContext.getSystemService(Service.NOTIFICATION_SERVICE);
        //判断是否添加进度
        if (max == 0) {
            notificationManager.notify(notifyId,mBuilder.build());
        }else {
            if (progress == max) {
                mBuilder.setContentText(finish);
                mBuilder.setProgress(0,0,false);
                notificationManager.notify(notifyId,mBuilder.build());
            }else {
                mBuilder.setProgress(max,progress,indeterminate);
                notificationManager.notify(notifyId,mBuilder.build());
            }
        }
    }

}
