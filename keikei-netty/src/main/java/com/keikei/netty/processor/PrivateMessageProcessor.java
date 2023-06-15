package com.keikei.netty.processor;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.enums.SendType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.IMsendInfo;
import com.keikei.common.domain.model.PrivateMessage;
import com.keikei.common.domain.model.ReceiveInfo;
import com.keikei.common.domain.model.SendResult;
import com.keikei.netty.nettys.IMServerGroup;
import com.keikei.netty.nettys.UserChannelCtxMap;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PrivateMessageProcessor extends MessageProcessor<ReceiveInfo<PrivateMessage>> {
    @Autowired
    private RedisCache redisCache;

    @Override
    public void processor(ReceiveInfo<PrivateMessage> data) {
        PrivateMessage message = transToPrivateMessage(data.getMessageBody());
        Long receiverId = data.getReceiverList().get(0);
        ChannelHandlerContext ctx = UserChannelCtxMap.getChannelCtx(receiverId);
        String key = CacheConstants.IM_RESULT_PRIVATE_QUEUE;
        SendResult sendResult = new SendResult(receiverId, message);
        try {
            if (ctx != null) {
                log.info("发送信息-发送者:{},接收者:{}", message.getSendId(), receiverId);
                IMsendInfo iMsendInfo = new IMsendInfo();
                iMsendInfo.setMessageBody(message);
                iMsendInfo.setMessageType(IMInfoType.PRIVATE_MESSAGE.code());
                ctx.channel().writeAndFlush(iMsendInfo);
                sendResult.setStatus(SendType.success);
            } else {
                sendResult.setStatus(SendType.not_online);
                log.debug("对方未连接ws,发送者:{},接收者:{}", message.getSendId(), receiverId);
            }

        } catch (Exception e) {
            sendResult.setStatus(SendType.not_channel);
            log.error("消息回复异常,发送者:{},接收者:{},内容:{},原因:{}", message.getSendId(), message.getReceiverId(), message.getText()
            ,e.getMessage());
        } finally {
            if (SendType.success.getCode() == sendResult.getStatus().getCode()) {
                //清除已发送消息
                String privateKey = CacheConstants.IM_PRIVATE_MESSAGE_QUE + IMServerGroup.serverId;
                redisCache.leftPop(privateKey);
            }
            //存储发送结果
            redisCache.rightPush(key, sendResult);
        }

    }

    public PrivateMessage transToPrivateMessage(Object o) {
        return (PrivateMessage) o;
    }
}
