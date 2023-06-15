package com.keikei.common.core.exception.user;

import com.keikei.common.constants.CacheConstants;

public class UserLoginCountException extends UserException{

    public UserLoginCountException(Object...args) {
        super("user.login.outCount", args);
    }
}
