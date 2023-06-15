package com.keikei.common.utils;

import com.keikei.common.domain.model.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.createEmptyContext().getAuthentication();
    }

    public static boolean matchPassword(String inputPassword, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(inputPassword,password);
    }

}
