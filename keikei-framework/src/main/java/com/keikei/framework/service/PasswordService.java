package com.keikei.framework.service;

import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.exception.user.UserException;
import com.keikei.common.core.exception.user.UserLoginCountException;
import com.keikei.common.core.exception.user.UserPasswordNotMatchException;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.common.domain.entity.SysUser;
import com.keikei.common.domain.model.KeikeiConfig;
import com.keikei.common.utils.PasswordEncodeUtils;
import com.keikei.common.utils.SecurityUtils;
import com.keikei.framework.security.context.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PasswordService {
    @Autowired
    private RedisCache redisCache;

    /**
     * 校验密码
     * @param user
     */
    public void validate(SysUser user){
        Authentication authentication = AuthenticationContextHolder.getUserNamePasswordInfo();
        String inputPassword = authentication.getCredentials().toString();
        String username = authentication.getName();
        Integer tryCount = KeikeiConfig.getTryCount();
        Integer redisTryCount = redisCache.getRedisObject(CacheConstants.LOGIN_TRY_COUNT+username);
        if(Objects.isNull(redisTryCount)){
            redisTryCount = new Integer(0);
        }
        if((redisTryCount=redisTryCount+1)>tryCount){
            throw new UserLoginCountException(tryCount);
        }
        if(!matchPassword(inputPassword,user.getPassword())){
            redisCache.setCacheObject(CacheConstants.LOGIN_TRY_COUNT+username,redisTryCount,15L, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        }
        else {
            clearTryCount(username);
        }
    }

    private void clearTryCount(String username) {
        redisCache.getRedisObject(CacheConstants.LOGIN_TRY_COUNT+username);
    }
    private boolean matchPassword(String inputPassword, String password) {
        return SecurityUtils.matchPassword(inputPassword,password);
    }
}
