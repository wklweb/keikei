package com.keikei.common.domain.model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.keikei.common.core.serializer.DateToLongSerializer;
import com.keikei.common.domain.entity.SysPrivateMessage;
import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.NotBlank;
import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

public class PrivateMessage implements Serializable {
    public static final Long serialVersionUID =1L;
    private String messageId;

    private Long sendId;//发送者
    private Long receiverId;
    private String text;
    private Integer type;
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date sendTime;

    public PrivateMessage() {
    }

    public PrivateMessage(Long sendId, Long receiverId, String text, Integer type) {
        this.sendId = sendId;
        this.receiverId = receiverId;
        this.text = text;
        this.type = type;
    }

    public PrivateMessage(SysPrivateMessage sysPrivateMessage) {
        this.messageId = sysPrivateMessage.getMessageId();
        this.sendId = sysPrivateMessage.getSendId();
        this.receiverId = sysPrivateMessage.getReceiverId();
        this.text = sysPrivateMessage.getText();
        this.type = sysPrivateMessage.getType();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Long getSendId() {
        return sendId;
    }
    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
