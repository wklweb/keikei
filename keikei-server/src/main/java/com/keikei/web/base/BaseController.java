package com.keikei.web.base;

import com.keikei.common.domain.model.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {

    public LoginUser getLoginUser(){
        return (LoginUser) getAuthentication().getPrincipal();
    }
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
