package com.keikei.client.task;

import cn.hutool.core.thread.ThreadUtil;
import com.keikei.netty.nettys.IMServerGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;

@Slf4j
public abstract class PullSendResultTask {

    @Autowired
    private IMServerGroup group;
    public ExecutorService service = ThreadUtil.newFixedExecutor(1,"from-pull-sendResult-",true);
    public abstract void dealWithSendResult();

    @PostConstruct
    public void init(){
        service.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
               try {
                   if(group.isReady()){
                       dealWithSendResult();
                   }
               }
               catch (Exception e){
                   Thread.sleep(200);
                   log.error("从redis拉取sendResult失败,原因:{}",e.getMessage());
               }
               if(!service.isShutdown()){
                   service.execute(this);
               }
            }
        });

    }
}
