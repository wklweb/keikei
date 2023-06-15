package com.keikei.common.domain.model;

import java.util.List;

public class ReceiveInfo<T> {
    private Integer messageType;

    private List<Long> receiverList;

    public List<Long> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<Long> receiverList) {
        this.receiverList = receiverList;
    }

    private T messageBody;

    public T getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(T messageBody) {
        this.messageBody = messageBody;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
}
