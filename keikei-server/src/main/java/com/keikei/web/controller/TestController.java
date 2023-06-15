package com.keikei.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.constants.UserStatus;
import com.keikei.common.core.exception.user.UserLoginCountException;
import com.keikei.common.core.exception.user.UserStatusException;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.model.KeikeiConfig;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.common.domain.model.ReceiveInfo;
import com.keikei.common.utils.SecurityUtils;
import com.keikei.common.utils.StringUtils;
import com.keikei.framework.security.web.service.TokenService;
import com.keikei.netty.nettys.IMServerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private KeikeiConfig config;

    @RequestMapping("/test")
    public void test(@RequestBody ReceiveInfo receiveInfo){
        String key = CacheConstants.IM_PRIVATE_MESSAGE_QUE+ IMServerGroup.serverId;
        ReceiveInfo[] receiveInfos = new ReceiveInfo[1];
        receiveInfos[0] = receiveInfo;
        redisCache.rightPushAll(key,receiveInfos);
    }
}
