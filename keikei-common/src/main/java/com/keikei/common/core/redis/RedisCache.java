package com.keikei.common.core.redis;

import com.keikei.common.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;
    public <T> T getRedisObject(String key){
        return (T) redisTemplate.opsForValue().get(key);
    }
    public void setCacheObject(String key, Object t, Long expireTime, TimeUnit minutes) {
        redisTemplate.opsForValue().set(key,t,expireTime,minutes);
    }
    public Long increment(String key,Long num){
        return redisTemplate.opsForValue().increment(key,num);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
    public void expire(String key,Long time,TimeUnit timeUnit){
        redisTemplate.expire(key,time,timeUnit);
    }
    public List range(String key, int i, int i1) {
        return redisTemplate.opsForList().range(key,i,i1);
    }

    public void rightPushAll(String key, Object...args) {
        redisTemplate.opsForList().rightPushAll(key,args);
    }
    public void leftPop(Object key) {
        redisTemplate.opsForList().leftPop(key);
    }

    public void rightPush(String key, Object sendResult) {
        redisTemplate.opsForList().rightPush(key,sendResult);
    }
    public GroupMessageSendResult getGroupMessageSendById(String key,Long index){
        return (GroupMessageSendResult) redisTemplate.opsForList().index(key,index);
    }
    public void updateGroupMessageSendResult(String key, long i, GroupMessageSendResult sendResult) {
        redisTemplate.boundListOps(key).set(i,sendResult);
    }
}
