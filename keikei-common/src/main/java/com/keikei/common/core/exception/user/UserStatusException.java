package com.keikei.common.core.exception.user;

public class UserStatusException extends UserException{


    public UserStatusException(Object... args) {
        super("user.status.exception", args);
    }
}
