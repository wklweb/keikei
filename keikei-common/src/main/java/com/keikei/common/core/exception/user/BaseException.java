package com.keikei.common.core.exception.user;

import com.keikei.common.utils.MessageUtils;
import com.keikei.common.utils.StringUtils;
import org.apache.logging.log4j.message.Message;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DelegatingMessageSource;

import java.util.Locale;

public class BaseException extends RuntimeException{
    static final long serialVersionUID = 1L;

    private Object[] params;
    private String module;
    private String defaultMsg;
    private String messageKey;

    public BaseException(Object[] params, String module, String defaultMsg, String code) {
        this.params = params;
        this.module = module;
        this.defaultMsg = defaultMsg;
        this.messageKey = code;
    }

    @Override
    public String getMessage() {
        String message = null;
        if(!StringUtils.isEmpty(messageKey)){
            message = MessageUtils.getMessage(messageKey,params);
            return message;
        }
        return defaultMsg;
    }


    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDefaultMsg() {
        return defaultMsg;
    }

    public void setDefaultMsg(String defaultMsg) {
        this.defaultMsg = defaultMsg;
    }

    public String getCode() {
        return messageKey;
    }

    public void setCode(String messageKey) {
        this.messageKey = messageKey;
    }
}
