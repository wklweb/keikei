package com.keikei.common.utils;

import org.apache.logging.log4j.message.Message;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceResourceBundle;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtils {

    public static String getMessage(String messageKey,Object...args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        String msg = messageSource.getMessage(messageKey,args, Locale.CHINESE);
        return msg;
    }
}
