package com.keikei.netty.task;

import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.PrivateMessage;
import com.keikei.common.domain.model.ReceiveInfo;
import com.keikei.netty.nettys.IMServerGroup;
import com.keikei.netty.processor.MessageProcessor;
import com.keikei.netty.processor.ProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PullPrivateMessage extends PullMessageTask {
    @Autowired
    private RedisCache redisCache;

    @Override
    public void pullMessage() {
        String key = CacheConstants.IM_PRIVATE_MESSAGE_QUE+IMServerGroup.serverId;
        List<ReceiveInfo> messages = redisCache.range(key, 0, -1);
        if(!messages.isEmpty()){
            for (Object o : messages) {
                ReceiveInfo<PrivateMessage> receiveInfo = (ReceiveInfo<PrivateMessage>) o;
                MessageProcessor messageProcessor = ProcessorFactory.createProcessor(IMInfoType.PRIVATE_MESSAGE);
                messageProcessor.processor(receiveInfo);
            }
        }
    }
}
