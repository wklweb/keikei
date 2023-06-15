package com.keikei.netty.task;

import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.GroupMessage;
import com.keikei.common.domain.model.PrivateMessage;
import com.keikei.common.domain.model.ReceiveInfo;
import com.keikei.netty.nettys.IMServerGroup;
import com.keikei.netty.processor.MessageProcessor;
import com.keikei.netty.processor.ProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PullGroupMessage extends PullMessageTask{
    @Autowired
    private RedisCache redisCache;
    @Override
    public void pullMessage() {
        String key = CacheConstants.IM_GROUP_MESSAGE_QUE + IMServerGroup.serverId;
        List<ReceiveInfo> receiveInfos = redisCache.range(key,0,-1);
        if(!receiveInfos.isEmpty()){
            for(ReceiveInfo o : receiveInfos){
                ReceiveInfo<GroupMessage> receiveInfo = (ReceiveInfo<GroupMessage>) o;
                MessageProcessor messageProcessor = ProcessorFactory.createProcessor(IMInfoType.GROUP_MESSAGE);
                messageProcessor.processor(receiveInfo);
            }
        }

    }
}
