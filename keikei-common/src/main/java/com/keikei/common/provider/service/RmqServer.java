package com.keikei.common.provider.service;

import com.alibaba.fastjson2.JSON;
import com.yueqiu.common.domain.SysMsgLog;
import com.yueqiu.common.domain.model.MailInfo;
import com.yueqiu.quartz.provider.constants.exchangeName;
import com.yueqiu.quartz.provider.constants.routingKeyName;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RmqServer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEmailMessage(MailInfo mailInfo){
        SysMsgLog sysMsgLog = new SysMsgLog();
        sysMsgLog.setMsgId(UUID.randomUUID().toString());
        sysMsgLog.setMsg(JSON.toJSONString(mailInfo));
        sysMsgLog.setResendCount(0);
        sysMsgLog.setExchange(exchangeName.DIRECT_EXCHANGE_EMAIL);
        sysMsgLog.setRoutingKey(routingKeyName.ROUTING_KEY_EMAIL);
        CorrelationData correlationData = new CorrelationData(sysMsgLog.getMsgId());
        Message message = MessageBuilder.withBody(JSON.toJSONBytes(sysMsgLog)).setMessageId(sysMsgLog.getMsgId())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8").build();

        correlationData.setReturned(new ReturnedMessage(message,302,"邮件发送失败",
                "myDirectExchange","my.direct.email"));
        rabbitTemplate.convertAndSend("myDirectExchange","my.direct.email",message,correlationData);
    }

}
