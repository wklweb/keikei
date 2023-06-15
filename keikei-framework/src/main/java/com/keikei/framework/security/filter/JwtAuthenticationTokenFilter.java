package com.keikei.framework.security.filter;

import com.keikei.common.domain.model.LoginUser;
import com.keikei.framework.security.context.AuthenticationContextHolder;
import com.keikei.framework.security.web.service.TokenService;
import com.keikei.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * token认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{

    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if(!Objects.isNull(loginUser)&&Objects.isNull(SecurityUtils.getAuthentication())){
            //刷新token
            tokenService.checkTimeOut(loginUser);
            //封装认证成功后的用户信息
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
            token.setDetails(loginUser);
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request,response);
    }
}
