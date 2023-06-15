package com.keikei.common.core.exception.user;

public class UserPasswordNotMatchException extends UserException{

    public UserPasswordNotMatchException() {
        super("user.password.notMatch", null);
    }
}
