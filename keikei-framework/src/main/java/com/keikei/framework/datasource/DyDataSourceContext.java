package com.keikei.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DyDataSourceContext {
    private static final Logger log = LoggerFactory.getLogger(DyDataSourceContext.class);
    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();


    public static String getDataSourceType(){
        return CURRENT.get();
    }
    public static void setDataSourceType(String value){
        log.info("选择数据源{}",value);
        CURRENT.set(value);
    }


}
