package com.keikei.common.core.global;

import com.keikei.common.core.exception.sevice.ServiceException;
import com.keikei.common.core.exception.user.UserPasswordNotMatchException;
import com.keikei.common.domain.AjaxResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public AjaxResult resolveServiceException(ServiceException e){
        AjaxResult ajaxResult = AjaxResult.error(e.getMessage());
        ajaxResult.put("code",302);
        System.out.println("yes");
        return ajaxResult;
    }
    @ExceptionHandler(UserPasswordNotMatchException.class)
    @ResponseBody
    public AjaxResult resolveServiceException(UserPasswordNotMatchException e){
        AjaxResult ajaxResult = AjaxResult.error(e.getMessage());
        ajaxResult.put("code",401);
        return ajaxResult;
    }



}
