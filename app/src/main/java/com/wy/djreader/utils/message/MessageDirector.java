package com.wy.djreader.utils.message;

public class MessageDirector {
    public MessageManager createMessageWhat(MessageBuilder builder){
        builder.buildHandler();
        builder.buildWhat();
        return builder.build();
    }
}
