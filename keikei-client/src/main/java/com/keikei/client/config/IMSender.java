package com.keikei.client.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.enums.MessageStatus;
import com.keikei.common.core.enums.MessageType;
import com.keikei.common.core.enums.SendType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.*;
import com.keikei.netty.nettys.IMServerGroup;
import com.keikei.netty.nettys.UserChannelCtxMap;
import com.keikei.system.service.SysGroupMessageService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Configuration
public class IMSender {
    @Autowired
    private RedisCache redisCache;
    public void sendPrivateMessage(Long receiverId, PrivateMessage[] messages) {
        ChannelHandlerContext context = UserChannelCtxMap.getChannelCtx(receiverId);
        ReceiveInfo[] receiveInfos = new ReceiveInfo[messages.length];
        String key = CacheConstants.IM_PRIVATE_MESSAGE_QUE+ IMServerGroup.serverId;
        if(context!=null){
            int i = 0;
            List<Long> ids = new ArrayList<>();
            for( PrivateMessage message : messages){
                ReceiveInfo receiveInfo = new ReceiveInfo();
                ids.add(receiverId);
                receiveInfo.setMessageType(IMInfoType.PRIVATE_MESSAGE.code());
                receiveInfo.setReceiverList(ids);
                receiveInfo.setMessageBody(message);
                receiveInfos[i] = receiveInfo;
                i++;
            }
            redisCache.rightPushAll(key,receiveInfos);
        }
        else {
            for(PrivateMessage message : messages){
                SendResult sendResult = new SendResult(receiverId,message,SendType.not_online);
                String sendKey = CacheConstants.IM_RESULT_PRIVATE_QUEUE;
                redisCache.rightPush(sendKey,sendResult);
            }
        }
    }

    public void sendGroupMessage(List<Long> receiverId, GroupMessage[] groupMessages) {
        if(!receiverId.isEmpty()){
            List<Long> noOnline = Collections.synchronizedList(new LinkedList<>());//创建线程安全的链表list
            Map<Integer,List<Long>> online = new ConcurrentHashMap();
            receiverId.parallelStream().forEach(rid->{
                String key = CacheConstants.IM_USER_SERVER_ID + rid;
                Integer serverId = Convert.toInt(redisCache.getRedisObject(key));
                if(!ObjUtil.isNull(serverId)){
                    synchronized (online){
                        if(online.containsKey(serverId)){
                            online.get(serverId).add(rid);
                        }
                        else {
                            List<Long> newList = Collections.synchronizedList(new LinkedList<>());
                            newList.add(rid);
                            online.put(serverId,newList);
                        }
                    }
                }else {
                    noOnline.add(rid);
                }
            });
            for(Map.Entry<Integer,List<Long>> m : online.entrySet()){
                 for(int i = 0;i<groupMessages.length;i++){
                     ReceiveInfo<GroupMessage> receiveInfo = new ReceiveInfo<>();
                     receiveInfo.setMessageType(IMInfoType.GROUP_MESSAGE.code());
                     receiveInfo.setMessageBody(groupMessages[i]);
                     receiveInfo.setReceiverList(new LinkedList<>(m.getValue()));
                     String key = CacheConstants.IM_GROUP_MESSAGE_QUE + m.getKey();
                     redisCache.rightPush(key,receiveInfo);
                 }
            }
            addGroupMessageSendResult(receiverId,groupMessages);
        }
    }
    public void addGroupMessageSendResult(List<Long> receiverIds,GroupMessage[] groupMessages){
        Arrays.asList(groupMessages).stream().forEach(groupMessage -> {
            String key = CacheConstants.IM_RESULT_GROUP_QUEUE;
            List<GroupMessageSendResult> groupMessageSendResults = redisCache.range(key,0,-1);
            boolean isEmpty = true;
            if(!groupMessageSendResults.isEmpty()){
                for(GroupMessageSendResult groupMessageSendResult : groupMessageSendResults){
                    if(groupMessageSendResult.getMessageId().equals(groupMessage.getMessageId())){
                        isEmpty = false;
                    }
                }
            }
            if(isEmpty){
                GroupMessageSendResult groupMessageSendResult = new GroupMessageSendResult();
                BeanUtil.copyProperties(groupMessage,groupMessageSendResult,true);
                groupMessageSendResult.setReceiverId(receiverIds);
                groupMessageSendResult.setMessageBody(groupMessage);
                redisCache.rightPushAll(key,groupMessageSendResult);
            }
        });
    }
}
