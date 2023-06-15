package com.keikei.web.controller;

import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson.JSON;
import com.keikei.common.constants.MimeTypeConstants;
import com.keikei.common.constants.UserConstants;
import com.keikei.common.domain.AjaxResult;
import com.keikei.common.domain.entity.SysUser;
import com.keikei.common.domain.model.LoginBody;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.common.utils.FileUtils;
import com.keikei.framework.annocation.Anonymous;
import com.keikei.framework.security.web.service.LoginService;
import com.keikei.framework.service.UserService;
import com.keikei.web.base.BaseController;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
public class SysUserController extends BaseController {
    @Autowired
    private LoginService service;
    @Autowired
    private UserService userService;
    public static final String AVATAR_LOCAL = "/avatar";

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        if (Objects.isNull(loginBody)) {
            return AjaxResult.error("账号/密码错误");
        }
        if (!loginBody.getUsername().matches(UserConstants.usernameRegex)) {
            return AjaxResult.error("用户名不合法输入");
        }
        if (!loginBody.getPassword().matches(UserConstants.passwordRegex)) {
            return AjaxResult.error("密码输入不合法输入");
        }
        String token = service.login(loginBody);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("token", token);
        return AjaxResult.success("登录成功", token);
    }

    @PostMapping("/avatar")
    public AjaxResult uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
        try {
            FileUtils.checkAllowFileType(avatar, MimeTypeConstants.IMAGE_TYPE);
            String url = FileUtils.checkAllowFile(avatar, MimeTypeConstants.IMAGE_TYPE, AVATAR_LOCAL);
            LoginUser loginUser = getLoginUser();

            userService.updateAvatarInfo(loginUser.getUser(), url);
            AjaxResult ajaxResult = AjaxResult.success();
            ajaxResult.put("url", url);
            ajaxResult.put("msg", "上传头像成功");
            return ajaxResult;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }

    }

    @PostMapping("/user/find/{userId}")
    public AjaxResult getUserInfo(@PathVariable @NotNull Long userId) {
        SysUser sysUser = userService.getUserById(userId);
        return AjaxResult.success("查询成功", ObjUtil.isNull(sysUser)?null:sysUser);
    }
}
