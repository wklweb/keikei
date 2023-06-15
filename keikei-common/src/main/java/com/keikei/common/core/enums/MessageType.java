package com.keikei.common.core.enums;

public enum MessageType {
    text(0,"文字"),img(1,"图片"),system(2,"系统");

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

    MessageType(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    MessageType() {
    }
}
