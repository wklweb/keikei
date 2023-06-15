package com.keikei.netty.task;

import com.keikei.netty.nettys.IMServerGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j
public abstract class PullMessageTask {
    private int threadNum = 1;
    public ExecutorService executor = Executors.newFixedThreadPool(threadNum);
    @Autowired
    private IMServerGroup group;

    @PostConstruct
    public void init(){
            executor.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                   try {
                       Thread.sleep(200);
                       if(group.isReady()){
                           pullMessage();
                       }
                   }
                   catch (Exception e){
                       Thread.sleep(200);
                       log.error("信息拉取失败,{}",e.getMessage());
                   }
                   if(!executor.isShutdown()){
                       executor.execute(this);
                   }
                }
            });

    }

    public abstract void pullMessage();
}
