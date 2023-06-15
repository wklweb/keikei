package com.keikei.netty.processor;

import cn.hutool.core.util.ObjUtil;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.enums.SendType;
import com.keikei.common.core.exception.sevice.ServiceException;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.*;
import com.keikei.netty.nettys.IMServerGroup;
import com.keikei.netty.nettys.UserChannelCtxMap;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GroupMessageProcessor extends MessageProcessor<ReceiveInfo<GroupMessage>> {
    @Autowired
    private RedisCache redisCache;

    @Override
    public void processor(ReceiveInfo<GroupMessage> data) {
        GroupMessage message = transToGroupMessage(data.getMessageBody());
        List<Long> receiverIds = data.getReceiverList();
        for (Long receiverId : receiverIds) {
            ChannelHandlerContext ctx = UserChannelCtxMap.getChannelCtx(receiverId);
            String key = CacheConstants.IM_RESULT_GROUP_QUEUE;
            try {
                //用户在线处理
                if (ctx != null) {
                    log.info("发送信息-发送者:{},接收者:{}", message.getSendId(), receiverId);
                    IMsendInfo iMsendInfo = new IMsendInfo();
                    iMsendInfo.setMessageBody(message);
                    iMsendInfo.setMessageType(IMInfoType.GROUP_MESSAGE.code());
                    ctx.channel().writeAndFlush(iMsendInfo);
                    changeSendResult(key, message.getMessageId(), receiverId, false);
                } else {//用户不在线处理
                    changeSendResult(key, message.getMessageId(), receiverId, true);
                    log.debug("对方未连接ws,发送者:{},接收者:{}", message.getSendId(), receiverId);
                }

            } catch (Exception e) {
                log.error("消息回复异常,发送者:{},接收者:{},内容:{},原因:{}", message.getSendId(), receiverId, message.getText()
                        , e.getMessage());
            } finally {
                key = CacheConstants.IM_GROUP_MESSAGE_QUE + IMServerGroup.serverId;
                redisCache.leftPop(key);
            }
        }
    }

    /**
     * 强转
     * @param o
     * @return
     */
    private GroupMessage transToGroupMessage(GroupMessage o) {
        return (GroupMessage) o;
    }

    /**
     * 修改 群聊消息接收者
     * @param key       redisKey
     * @param messageId 消息id
     * @param userId    用户id
     * @param sign      (true:增加未读，false:减少未读)
     */
    public void changeSendResult(String key, String messageId, Long userId, boolean sign) {
        List<GroupMessageSendResult<GroupMessage>> groupMessageSendResults = redisCache.range(key, 0, -1);
        long i = 0;
        for (GroupMessageSendResult sendResult : groupMessageSendResults) {
            if (sendResult.getMessageId().equals(messageId)) {
                GroupMessageSendResult groupMessageSendResult = redisCache.getGroupMessageSendById(key, i);
                synchronized (groupMessageSendResult) {
                    List<Long> r = groupMessageSendResult.getReceiverId();
                    if (sign) {//增加该用户未读消息
                        if (!r.contains(userId)) {
                            r.add(userId);
                            sendResult.setReceiverId(r);
                            redisCache.updateGroupMessageSendResult(key, i, sendResult);
                        }
                    } else {//标记该用户已读信息
                        if (r.contains(userId)) {
                            int index = r.indexOf(userId);
                            r.remove(index);
                            sendResult.setReceiverId(r);
                            redisCache.updateGroupMessageSendResult(key, i, sendResult);
                        }
                    }
                }
            }
            i++;
        }
    }


}