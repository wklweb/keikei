package com.keikei.framework.manager;

import com.keikei.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;
import java.util.concurrent.Executor;

/**
 * 异步管理器
 */
@Slf4j
public class AsyncManager {

    private Executor cacheThreadPool = SpringUtils.getBean("cacheThreadPool");
    public static AsyncManager me = new AsyncManager();

    public static AsyncManager me(){
        return me;
    }

    public void execute(TimerTask task){
        cacheThreadPool.execute(task);
    }



}
