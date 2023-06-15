package com.keikei.common.core.exception.sevice;

public class ServiceException extends RuntimeException{
    private static final Long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private String defaultMessage;

    @Override
    public String getMessage() {
        return this.msg;
    }

    public ServiceException() {
    }

    public ServiceException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
