package com.keikei.common.core.enums;

public enum MessageStatus {
    UNREAD(0,"未读"),READ(1,"已读"),RECALL(2,"撤回");
    private Integer code;
    private String info;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    MessageStatus(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    MessageStatus() {
    }
}
