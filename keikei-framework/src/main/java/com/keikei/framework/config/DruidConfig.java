package com.keikei.framework.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.keikei.framework.config.properties.DruidProperties;
import com.keikei.framework.datasource.DyDataSource;
import com.keikei.framework.datasource.DyDataSourceContext;
import com.keikei.framework.datasource.enums.DataSourceType;
import com.keikei.common.utils.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource masterDataSource(DruidProperties properties){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return properties.dataSource(dataSource);
    }
    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource(DruidProperties properties){
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        return properties.dataSource(dataSource);
    }
    @Bean(name = "dynamicDataSource")
    @Primary
    public DyDataSource dyDataSource(DataSource masterDataSource){
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.Master.name(), masterDataSource);
        setDataSource(targetDataSources,DataSourceType.Slave.name(),"slave");
        DyDataSourceContext.setDataSourceType("master");
        return new DyDataSource(masterDataSource,targetDataSources);
    }

    private void setDataSource(Map<Object, Object> targetDataSources, String name, String slave) {
        try{
            DataSource source = SpringUtils.getBean(slave);
            targetDataSources.put(name,source);
        }
        catch (Exception e){

        }
    }

}
