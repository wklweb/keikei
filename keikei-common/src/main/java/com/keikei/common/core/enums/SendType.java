package com.keikei.common.core.enums;

public enum SendType {
    success(0,"发送成功"),
    not_online(1,"目标不在线"),
    not_channel(2,"channel找不到"),
    error(3,"异常");

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

    SendType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }
}
