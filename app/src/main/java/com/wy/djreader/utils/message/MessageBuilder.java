package com.wy.djreader.utils.message;

import android.os.Bundle;
import android.os.Handler;

/**
 * 消息管理抽象建造者
 */
public abstract class MessageBuilder {
    private MessageManager msgManager = new MessageManager();

    public abstract Handler buildHandler();
    public abstract int buildWhat();
    public abstract int buildArg1();
    public abstract int buildArg2();
    public abstract Object buildObject();
    public abstract Bundle buildBundle();

    public MessageManager build(){
        return msgManager;
    }
}
