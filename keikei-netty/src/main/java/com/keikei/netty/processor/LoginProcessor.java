package com.keikei.netty.processor;

import cn.hutool.core.bean.BeanUtil;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.IMsendInfo;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.netty.nettys.IMServerGroup;
import com.keikei.netty.nettys.UserChannelCtxMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LoginProcessor extends MessageProcessor<LoginUser> {
    @Autowired
    private RedisCache redisCache;

    @Override
    public void processor(ChannelHandlerContext ctx, LoginUser data) {
        log.info("用户登录，userId:{}",data.getUserId());
        ChannelHandlerContext context = UserChannelCtxMap.getChannelCtx(data.getUserId());
        if(context!=null){
            //多客户端登录
            IMsendInfo iMsendInfo = new IMsendInfo();
            iMsendInfo.setMessageType(IMInfoType.FORCE_LOGUT.code());
            context.channel().writeAndFlush(iMsendInfo);
        }
        UserChannelCtxMap.addChannelCtx(data.getUserId(),ctx);
        AttributeKey<Long> attr = AttributeKey.valueOf("user_id");
        ctx.channel().attr(attr).set(data.getUserId());
        // 心跳次数
        attr = AttributeKey.valueOf("HEARTBEAt_TIMES");
        ctx.channel().attr(attr).set(0L);
        String key = CacheConstants.IM_USER_SERVER_ID+data.getUserId();
        redisCache.setCacheObject(key, IMServerGroup.serverId,60L, TimeUnit.SECONDS);
        //向客户端响应登录
        IMsendInfo iMsendInfo = new IMsendInfo();
        iMsendInfo.setMessageType(IMInfoType.LOGIN.code());
        ctx.channel().writeAndFlush(iMsendInfo);

    }
    @Override
    public LoginUser transForm(Object o){
        HashMap hashMap = (HashMap) o;
        LoginUser loginUser = BeanUtil.fillBeanWithMap(hashMap,new LoginUser(),false);
        return loginUser;
    }

}
