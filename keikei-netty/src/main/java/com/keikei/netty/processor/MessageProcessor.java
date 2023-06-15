package com.keikei.netty.processor;

import com.keikei.common.domain.model.PrivateMessage;
import io.netty.channel.ChannelHandlerContext;

public abstract class MessageProcessor<T> {

    public void processor(T data){};

    public void processor(ChannelHandlerContext ctx, T data) {}

    public T transForm(Object o){
        return (T)o;
    }

}
