package com.keikei.client.service.Impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.keikei.client.service.OnlineService;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class OnlineServiceImpl implements OnlineService {

    @Autowired
    private RedisCache redisCache;
    public static String key = CacheConstants.IM_USER_SERVER_ID;


    @Override
    public List<String> filterNotOnlineUserId(String useId) {
        String[] ids = useId.trim().split(",");
        List<String> online = new LinkedList<>();
        for(String str : ids){
            str = StrUtil.removeAll(str,"\"");
            if(isOnline(str)){
                online.add(str);
            }
        }
        return online;
    }

    private boolean isOnline(String str) {
         Long severId = redisCache.getRedisObject(key+str);
         return ObjUtil.isNull(severId)?false:true;
    }
}
