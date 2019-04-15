package com.wy.djreader.utils.message;

import android.os.Bundle;
import android.os.Handler;

public class MessageWhat extends MessageBuilder {
    @Override
    public Handler buildHandler() {
        return null;
    }

    @Override
    public int buildWhat() {
        return 0;
    }

    @Override
    public int buildArg1() {
        return 0;
    }

    @Override
    public int buildArg2() {
        return 0;
    }

    @Override
    public Object buildObject() {
        return null;
    }

    @Override
    public Bundle buildBundle() {
        return null;
    }
}
