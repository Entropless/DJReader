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

    public MessageManager() {
        this(new MessageBuilder());
    }
    MessageManager(MessageBuilder builder){
        this.handler = builder.handler;
        this.what = builder.what;
        this.arg1 = builder.arg1;
        this.arg2 = builder.arg2;
        this.object = builder.object;
        this.data = builder.bundle;
    }

    public static class MessageBuilder{
        Handler handler;
        int what,arg1,arg2;
        Object object;
        Bundle bundle;

        public MessageBuilder() {
        }

        MessageBuilder(MessageManager msg){
            this.handler = msg.handler;
            this.what = msg.what;
            this.arg1 = msg.arg1;
            this.arg2 = msg.arg2;
            this.bundle = msg.data;
            this.object = msg.object;
        }
        public MessageBuilder setHandler(Handler handler){
            this.handler = handler;
            return this;
        }
        public MessageBuilder setWhat(int what){
            this.what = what;
            return this;
        }
        public MessageBuilder setArg1(int Arg1){
            this.arg1 = arg1;
            return this;
        }
        public MessageBuilder setArg2(int Arg2){
            this.arg2 = arg2;
            return this;
        }
        public MessageBuilder setObject(Object object){
            this.object = object;
            return this;
        }
        public MessageBuilder setBundle(Bundle bundle){
            this.bundle = bundle;
            return this;
        }
        public MessageManager build(){
            if (handler == null) throw new IllegalStateException("handler == null");
            return new MessageManager(this);
        }
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
