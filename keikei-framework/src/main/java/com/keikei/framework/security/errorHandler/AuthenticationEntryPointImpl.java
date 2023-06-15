package com.keikei.framework.security.errorHandler;

import com.alibaba.fastjson.JSON;
import com.keikei.common.constants.HttpStatus;
import com.keikei.common.domain.AjaxResult;
import com.keikei.common.utils.ServletUtils;
import com.keikei.common.utils.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类
 */
@Service
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint,Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求路径{}失败,请先认证",request.getRequestURI());
        ServletUtils.resendString(response, JSON.toJSONString(AjaxResult.error(code,msg)));
    }
}
