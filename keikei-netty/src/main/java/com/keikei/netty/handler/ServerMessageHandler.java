package com.keikei.netty.handler;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.IMsendInfo;
import com.keikei.common.utils.SpringUtils;
import com.keikei.netty.nettys.UserChannelCtxMap;
import com.keikei.netty.processor.MessageProcessor;
import com.keikei.netty.processor.ProcessorFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ServerMessageHandler extends SimpleChannelInboundHandler<IMsendInfo> {

    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                // 在规定时间内没有收到客户端的上行数据, 主动断开连接
                AttributeKey<Long> attr = AttributeKey.valueOf("user_id");
                Long userId = ctx.channel().attr(attr).get();
                log.info("心跳超时，即将断开连接,用户id:{} ",userId);
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("用户:{},连接服务成功！",ctx.channel().remoteAddress());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        AttributeKey<Long> attributeKey = AttributeKey.valueOf("user_id");
        Long userId = ctx.channel().attr(attributeKey).get();
        ChannelHandlerContext context = UserChannelCtxMap.getChannelCtx(userId);
        if(context!=null&&context.channel().id().equals(ctx.channel().id())){
            UserChannelCtxMap.removeChannelCtx(userId);
            RedisCache redisCache = SpringUtils.getBean("redisCache");
            String key = CacheConstants.IM_USER_SERVER_ID+userId;
            redisCache.delete(key);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, IMsendInfo sendInfo) throws Exception {
        MessageProcessor processor = ProcessorFactory.createProcessor(IMInfoType.fromCode(sendInfo.getMessageType()));
        processor.processor(channelHandlerContext,processor.transForm(sendInfo.getMessageBody()));
    }
}
