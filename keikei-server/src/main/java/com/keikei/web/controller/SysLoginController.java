package com.keikei.web.controller;

import com.keikei.common.domain.AjaxResult;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.web.base.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class SysLoginController extends BaseController {

    @PostMapping("/getUserInfo")
    public AjaxResult getUserInfo(){
        LoginUser loginUser = getLoginUser();
        if(!Objects.isNull(loginUser)){
            AjaxResult ajaxResult = AjaxResult.success("获取用户信息成功");
            ajaxResult.put("user",loginUser.getUser());
            ajaxResult.put("permissions",loginUser.getPermissions());
            return ajaxResult;
        }
        return AjaxResult.error("请先登录");
    }

}
