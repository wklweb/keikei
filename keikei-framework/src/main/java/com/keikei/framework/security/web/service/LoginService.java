package com.keikei.framework.security.web.service;

import com.keikei.common.core.exception.sevice.ServiceException;
import com.keikei.common.core.exception.user.UserPasswordNotMatchException;
import com.keikei.common.domain.model.LoginBody;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.framework.security.context.AuthenticationContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public String login(LoginBody loginBody) {
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
        Authentication authentication = null;
        try{
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    username, password
            );
            AuthenticationContextHolder.setUserNamePasswordInfo(usernamePasswordAuthenticationToken);
            authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        }
        catch (Exception e){
            if(e instanceof BadCredentialsException){
                throw new UserPasswordNotMatchException();
            }
            else{
                throw new ServiceException(e.getMessage());
            }
        }
        finally {
            AuthenticationContextHolder.clearContext();//清空用户输入身份信息
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return tokenService.createToken(loginUser);



    }


}
