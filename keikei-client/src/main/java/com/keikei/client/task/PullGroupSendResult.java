package com.keikei.client.task;

import com.keikei.client.listen.ListenDistribution;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.SendResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PullGroupSendResult extends PullSendResultTask{

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ListenDistribution listenDistribution;

    @Override
    public void dealWithSendResult() {
        String key = CacheConstants.IM_RESULT_GROUP_QUEUE;
        List<SendResult> sendResult = redisCache.range(key,0,-1);
        listenDistribution.distribution(IMInfoType.GROUP_MESSAGE,sendResult);
    }
}
