package com.keikei.framework.security.successHandler;

import com.alibaba.fastjson.JSON;
import com.keikei.common.domain.AjaxResult;
import com.keikei.common.utils.ServletUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String msg = "注销成功";
        ServletUtils.resendString(response, JSON.toJSONString(AjaxResult.success(msg)));
    }
}
