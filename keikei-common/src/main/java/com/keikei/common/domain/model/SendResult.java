package com.keikei.common.domain.model;

import com.keikei.common.core.enums.SendType;

import java.io.Serializable;

public class SendResult<T> implements Serializable {

    public static final Long serialVersionUID = 1L;
    private Long receiverId;
    private T messageBody;
    private SendType status;
    public SendResult() {
    }

    public SendResult(Long receiverId, T messageBody) {
        this.receiverId = receiverId;
        this.messageBody = messageBody;
    }

    public SendResult(Long receiverId, T messageBody, SendType status) {
        this.receiverId = receiverId;
        this.messageBody = messageBody;
        this.status = status;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public T getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(T messageBody) {
        this.messageBody = messageBody;
    }

    public SendType getStatus() {
        return status;
    }

    public void setStatus(SendType status) {
        this.status = status;
    }
}
