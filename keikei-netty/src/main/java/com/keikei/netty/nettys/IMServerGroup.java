package com.keikei.netty.nettys;

import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.redis.RedisCache;
import com.keikei.netty.nettys.IMServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动netty 服务程序
 */
@Slf4j
@Component
public class IMServerGroup implements CommandLineRunner {
    public static volatile Long serverId = 0L;
    @Autowired
    public List<IMServer> servers;
    @Autowired
    private RedisCache redisCache;

    public boolean isReady(){
        for(IMServer server : servers){
            if(!server.isReady()){
                return false;
            }
        }
        return true;
    }
    @Override
    public void run(String... args) throws Exception {
        String key = CacheConstants.IM_MAX_SERVER_ID;
        serverId = redisCache.increment(key,1L);
        for(IMServer server : servers){
            server.start();
        }
    }
    @PreDestroy
    public void destroy(){
        for(IMServer server : servers){
            server.close();
        }
    }
}
