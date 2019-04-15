package com.wy.djreader.utils.message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 使用建造者模式构建消息管理类
 * @author wy
 */
public class MessageManager {
    private Handler handler;
    private int what;
    private int arg1;
    private int arg2;
    private Object object;
    private Bundle data;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public int getArg1() {
        return arg1;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Bundle getData() {
        return data;
    }

    public void setData(Bundle data) {
        this.data = data;
    }

    public MessageManager() { }

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

    static class MessageManagerBuilder{
        int arg1 = 1;
    }

    public MessageManager(MessageManagerBuilder builder){
        this.arg1 = builder.arg1;
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
