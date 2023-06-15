package com.keikei.common.domain.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.keikei.common.core.base.BaseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysPrivateMessage implements Serializable {

    public static final Long serialVersionUID = 1L;

    @NotNull(value = "id不能为空")
    private String messageId;

    private Long sendId;

    private Long receiverId;

    private String text;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date sendTime;

    private Integer status;

    private Integer type;

    @NotNull
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public Date getSendTime() {
        return sendTime;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SysPrivateMessage{" +
                "messageId=" + messageId +
                ", sendId=" + sendId +
                ", receiverID=" + receiverId +
                ", text='" + text + '\'' +
                ", sendTime=" + sendTime +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
