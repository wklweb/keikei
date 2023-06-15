package com.keikei.framework.security.context;

import org.springframework.security.core.Authentication;

public class AuthenticationContextHolder {
    private static ThreadLocal<Authentication> current = new ThreadLocal<>();

    public static void setUserNamePasswordInfo(Authentication authentication){
        current.set(authentication);
    }

    public static Authentication getUserNamePasswordInfo(){
        return current.get();
    }

    public static void clearContext() {
        current.remove();
    }
}
