package com.keikei.common.provider.config;
import com.yueqiu.quartz.consumer.listen.MailListen;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息监听器
 */
@Configuration
public class MessageListenConfig {
    public static String EMAIL_QUEUE = "emailQueue";

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    private MailListen mailListen;

    /**
     * 监听邮件发送
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cachingConnectionFactory);
        container.setMessageListener(mailListen);
        container.setQueueNames(EMAIL_QUEUE);
        // 手动确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }



}
