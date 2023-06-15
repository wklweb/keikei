package com.keikei.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatHeartbeatHandler extends ChannelInboundHandlerAdapter {

    private final Logger log = LogManager.getLogger();
    private final ByteBuf bf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HB", CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){//处于空闲状态
            log.info("====>Heartbeat: greater than {}");
            ctx.writeAndFlush(bf.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        }
        else {
            super.userEventTriggered(ctx,evt);
        }
    }
}
