package com.keikei.common.config;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPool {


    @Bean("cacheThreadPool")
    public Executor cacheThreadPool(){
        return Executors.newCachedThreadPool(
                new BasicThreadFactory.Builder().namingPattern("cache-pool-%d").daemon(false).build()
        );
    }


}
