package com.keikei.common.domain.model;

/**
 * 发送消息实体
 */
public class IMsendInfo<T> {
    /**
     * 消息类型
     */
    private Integer messageType;

    private T messageBody;

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public T getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(T messageBody) {
        this.messageBody = messageBody;
    }
}
