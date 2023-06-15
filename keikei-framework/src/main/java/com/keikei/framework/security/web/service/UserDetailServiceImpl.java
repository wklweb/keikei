package com.keikei.framework.security.web.service;

import com.keikei.common.constants.HttpStatus;
import com.keikei.common.constants.UserStatus;
import com.keikei.common.core.exception.sevice.ServiceException;
import com.keikei.common.core.exception.user.UserLoginCountException;
import com.keikei.common.core.exception.user.UserStatusException;
import com.keikei.common.domain.entity.SysUser;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.framework.service.PasswordService;
import com.keikei.framework.service.PermissionService;
import com.keikei.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectUserByName(username);
        if (!Objects.isNull(sysUser)) {
            passwordService.validate(sysUser);
            checkUserStatus(sysUser);
            return CreateLoginUser(sysUser);
        }
        else {
            throw new ServiceException(HttpStatus.error, "用户不存在");
        }
    }

    private UserDetails CreateLoginUser(SysUser sysUser) {
        LoginUser loginUser = new LoginUser(sysUser.getUserId(),permissionService.setPermission(sysUser),sysUser);
        return loginUser;
    }


    public void checkUserStatus(SysUser sysUser) {
        String status = sysUser.getStatus();
        if (status.equals(UserStatus.stop.getCode())) {
            throw new UserStatusException(UserStatus.stop.getInfo());
        } else if (status.equals(UserStatus.noActive.getCode())) {
            throw new UserStatusException(UserStatus.stop.getInfo());
        }
    }
}
