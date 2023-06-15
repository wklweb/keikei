package com.keikei.common.provider.config;

import com.keikei.common.provider.constants.exchangeName;
import com.keikei.common.provider.constants.queueName;
import com.keikei.common.provider.constants.routingKeyName;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {
    @Bean
    public DirectExchange directExchange(){
        /**
         * 交换机名称，是否持久化，是否自动删除
         */
        return new DirectExchange(exchangeName.DIRECT_EXCHANGE_ADD,true,false);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue(queueName.ADD_QUEUE,true);
    }
    @Bean
    public Binding bindingDirect2(){
        return BindingBuilder.bind(emailQueue()).to(directExchange()).with(routingKeyName.ROUTING_KEY_ADD);
    }

}
