package com.keikei.client.config;

import com.keikei.common.domain.model.GroupMessage;
import com.keikei.common.domain.model.PrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class IMClient {

    @Autowired
    private IMSender sender;

    public void sendPrivateMessage(PrivateMessage privateMessage){
        sender.sendPrivateMessage(privateMessage.getReceiverId(),new PrivateMessage[]{privateMessage});
    }

    public void sendGroupMessage(List<Long> receiverId, GroupMessage[] groupMessages) {
        sender.sendGroupMessage(receiverId,groupMessages);
    }
}
