package com.keikei.framework.config;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class MybatisConfig {

    @Autowired
    private Environment environment;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource source) throws Exception {
        String typeAlias = environment.getProperty("mybatis.type-aliases-package");
        String mapperLocations = environment.getProperty("mybatis.mapper-locations");
        String configLocation = environment.getProperty("mybatis.config-location");
        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(source);
        VFS.addImplClass(SpringBootVFS.class);
        typeAlias = setTypeAlias(typeAlias);
        sqlSessionFactory.setTypeAliasesPackage(typeAlias);
        sqlSessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        sqlSessionFactory.setMapperLocations(resolveMapperLocations(StringUtils.split(mapperLocations,",")));
        return sqlSessionFactory.getObject();
    }

    private Resource[] resolveMapperLocations(String[] split) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> strings = new ArrayList<>();
        if(split.length>0){
            for(String s : split){
                try {
                    Resource[] resources = resolver.getResources(s);
                    strings.addAll(Arrays.asList(resources));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return strings.toArray(new Resource[strings.size()]);
    }

    private String setTypeAlias(String typeAlias) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        List<String> t = new ArrayList<>();
        String[] types = typeAlias.split(",");
       try{
           List<String> list = new ArrayList<>();
           for(String s : types){
                s = resolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(s.trim())
                        +"/**/*.class";
                Resource[] resources = resolver.getResources(s);
                for(Resource resource : resources){
                    MetadataReader reader = metadataReaderFactory.getMetadataReader(resource);
                    list.add(Class.forName(reader.getClassMetadata().getClassName()).getPackage().getName());
                }
               if(list.size()>0){
                   HashSet<String> set = new HashSet(list);
                   t.addAll(set);
               }
           }
           if(t.size()>0){
                String[] strs = t.toArray(new String[0]);
                typeAlias = String.join(",",strs);
           }
            else {
                throw new RuntimeException("找不到typeAlias包");
           }

       }
       catch (Exception e){
           e.printStackTrace();
       }
       return typeAlias;
    }

}
