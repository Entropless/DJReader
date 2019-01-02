package com.wy.djreader.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MessageManager {
    private Handler handler;
    private int what;
    private int arg1;
    private int arg2;
    private Object object;
    private Bundle data;

    public MessageManager(Handler handler, int what) {
        this.handler = handler;
        this.what = what;
    }

    public MessageManager(Handler handler, int what, int arg1, int arg2) {
        this.handler = handler;
        this.what = what;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public MessageManager(Handler handler, int what, Object object) {
        this.handler = handler;
        this.what = what;
        this.object = object;
    }

    public MessageManager(Handler handler, int what, Bundle data) {
        this.handler = handler;
        this.what = what;
        this.data = data;
    }

    public MessageManager(Handler handler, int what, Object object, Bundle data) {
        this.handler = handler;
        this.what = what;
        this.object = object;
        this.data = data;
    }

    public void sendMessage(){
        if (null == handler) return;
        Message msg = handler.obtainMessage();
        msg.what = what;
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        if (object != null){
            msg.obj = object;
        }
        if (data != null) {
            msg.setData(data);
        }
        handler.sendMessage(msg);
    }
}
