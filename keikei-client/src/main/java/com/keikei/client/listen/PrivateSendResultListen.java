package com.keikei.client.listen;

import com.keikei.client.config.IMClient;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.enums.MessageStatus;
import com.keikei.common.core.enums.SendType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.PrivateMessage;
import com.keikei.common.domain.model.ReceiveInfo;
import com.keikei.common.domain.model.SendResult;
import com.keikei.framework.annocation.SRListen;
import com.keikei.framework.manager.AsyncFactory;
import com.keikei.framework.manager.AsyncManager;
import com.keikei.netty.nettys.IMServerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@SRListen(type = IMInfoType.PRIVATE_MESSAGE)
public class PrivateSendResultListen implements SendResultListen<SendResult> {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IMClient imClient;
    @Override
    public void businessPolling(List<SendResult> sendResult) {
        String key = CacheConstants.IM_RESULT_PRIVATE_QUEUE;
        if (!sendResult.isEmpty()) {
            for (SendResult result : sendResult) {
                PrivateMessage privateMessage = (PrivateMessage) result.getMessageBody();
                if (result.getStatus().equals(SendType.success)) {
                    AsyncManager.me().execute(AsyncFactory.updatePrivateMessage(privateMessage, MessageStatus.READ.getCode()));
                }
                else {
                    imClient.sendPrivateMessage(privateMessage);
                }
                redisCache.leftPop(key);
            }
        }
    }
}
