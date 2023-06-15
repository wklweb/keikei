package com.keikei.common.constants;

public enum UserStatus {
    stop(0,"停止"),normal(1,"正常"),noActive(2,"未激活"),noOnline(3,"未在线"),
    Online(4, "在线");

    private int code;
    private String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    UserStatus(int code, String info) {
        this.code = code;
        this.info = info;
    }
}
