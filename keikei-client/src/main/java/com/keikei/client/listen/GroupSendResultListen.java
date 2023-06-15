package com.keikei.client.listen;

import com.keikei.client.config.IMClient;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.enums.MessageStatus;
import com.keikei.common.core.enums.SendType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.entity.SysGroupMessage;
import com.keikei.common.domain.model.*;
import com.keikei.framework.annocation.SRListen;
import com.keikei.framework.manager.AsyncFactory;
import com.keikei.framework.manager.AsyncManager;
import com.keikei.netty.nettys.IMServerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SRListen(type = IMInfoType.GROUP_MESSAGE)
public class GroupSendResultListen implements SendResultListen<GroupMessageSendResult> {
    @Autowired
    private RedisCache redisCache;
    public String key = CacheConstants.IM_RESULT_GROUP_QUEUE;
    @Override
    public void businessPolling(List<GroupMessageSendResult> sendResult) {
        if (!sendResult.isEmpty()) {//处理redis内 所有 GroupMessageSendResult
            for (GroupMessageSendResult result : sendResult) {
                GroupMessage groupMessage = (GroupMessage) result.getMessageBody();
                if (result.getReceiverId().isEmpty()) {
                    //更新消息状态->已读
                    SysGroupMessage sysGroupMessage = new SysGroupMessage();
                    sysGroupMessage.setMessageId(groupMessage.getMessageId());
                    sysGroupMessage.setStatus(MessageStatus.READ.getCode());
                    AsyncManager.me().execute(AsyncFactory.updateGroupMessage(sysGroupMessage));
                    redisCache.leftPop(key);
                } else {
                    String messageKey = CacheConstants.IM_GROUP_MESSAGE_QUE + IMServerGroup.serverId;
                    List<ReceiveInfo<GroupMessage>> list = redisCache.range(messageKey, 0, -1);
                    if (!list.isEmpty()) {
                        for (ReceiveInfo r : list) {
                            GroupMessage message = (GroupMessage) r.getMessageBody();
                            if (message.getMessageId().equals(groupMessage.getMessageId())) {
                                return;
                            }
                        }
                        addGroupMessage(groupMessage, result.getReceiverId());
                    } else {
                        addGroupMessage(groupMessage, result.getReceiverId());
                    }
                }
            }
        }
    }

    /**
     * 重新发送 GroupMessage(群聊消息)
     * @param groupMessage
     * @param receiverIds
     */
    public void addGroupMessage(GroupMessage groupMessage, List<Long> receiverIds) {
        String messageKey = CacheConstants.IM_GROUP_MESSAGE_QUE + IMServerGroup.serverId;
        ReceiveInfo receiveInfo = new ReceiveInfo();
        receiveInfo.setMessageBody(groupMessage);
        receiveInfo.setMessageType(groupMessage.getType());
        receiveInfo.setReceiverList(receiverIds);
        redisCache.rightPush(messageKey, receiveInfo);
    }
}
