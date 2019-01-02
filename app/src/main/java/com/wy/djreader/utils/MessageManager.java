package com.wy.djreader.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MessageManager {
    public static void sendMessage(Handler handler, int what, int arg, Bundle data){
        Message msg = handler.obtainMessage();
        if (null == handler) return;
        msg.what = what;
        msg.arg1 = arg;
        if (null == data) {
            handler.sendMessage(msg);
        }else {
            msg.setData(data);
            handler.sendMessage(msg);
        }
    }
}
