package com.keikei.common.domain.model;

import java.io.Serializable;
import java.util.List;

public class GroupMessageSendResult<T> implements Serializable {
    public static final Long serialVersionUID =1L;
    private String messageId;
    private Long groupId;
    private List<Long> receiverId;
    private T messageBody;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Long> getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(List<Long> receiverId) {
        this.receiverId = receiverId;
    }

    public T getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(T messageBody) {
        this.messageBody = messageBody;
    }
}
