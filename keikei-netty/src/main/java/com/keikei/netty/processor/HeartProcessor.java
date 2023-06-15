package com.keikei.netty.processor;

import cn.hutool.core.bean.BeanUtil;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.HeartInfo;
import com.keikei.common.domain.model.IMsendInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class HeartProcessor extends MessageProcessor<HeartInfo> {
    @Autowired
    private RedisCache redisCache;

    @Override
    public void processor(ChannelHandlerContext ctx, HeartInfo data) {
        IMsendInfo iMsendInfo = new IMsendInfo();
        iMsendInfo.setMessageType(IMInfoType.HEART_BEAT.code());
        ctx.channel().writeAndFlush(iMsendInfo);
        String key = CacheConstants.IM_USER_SERVER_ID+data.getUserId();
        Channel channel = ctx.channel();
        AttributeKey<Long> attr = AttributeKey.valueOf("HEARTBEAt_TIMES");
        Long heartCount = channel.attr(attr).get();
        channel.attr(attr).set(++heartCount);
        if(heartCount%10==0){
            redisCache.expire(key,600L,TimeUnit.SECONDS);
        }

    }

    @Override
    public HeartInfo transForm(Object o) {
        HashMap hashMap = (HashMap) o;
        return BeanUtil.fillBeanWithMap(hashMap,new HeartInfo(),false);
    }
}
