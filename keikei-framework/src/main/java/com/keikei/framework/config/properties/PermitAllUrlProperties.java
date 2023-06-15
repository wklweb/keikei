package com.keikei.framework.config.properties;

import com.keikei.framework.annocation.Anonymous;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Configuration
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware {
    public List<String> urls = new ArrayList<>();
    public ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerMapping mappingHandlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo,HandlerMethod> map =  mappingHandlerMapping.getHandlerMethods();
        map.keySet().forEach(requestMappingInfo -> {
            HandlerMethod method = map.get(requestMappingInfo);
            Anonymous anonymous = AnnotationUtils.findAnnotation(method.getMethod(),Anonymous.class);
            Optional.ofNullable(anonymous).ifPresent(anonymous1 -> {
                Objects.requireNonNull(requestMappingInfo.getPatternsCondition().getPatterns()).forEach(
                        url->urls.add(url)
                );
            });
            Anonymous anonymousByClass = AnnotationUtils.findAnnotation(method.getBeanType(), Anonymous.class);
            Optional.ofNullable(anonymousByClass).ifPresent(anonymous1 -> {
                Objects.requireNonNull(requestMappingInfo.getPatternsCondition().getPatterns()).forEach(
                        url->urls.add(url)
                );
            });
        });

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
