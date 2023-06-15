package com.keikei.common.core.exception.user;

public class UserException extends BaseException{

    public UserException(String code,Object...args) {
        super(args, "user", null, code);
    }
}
