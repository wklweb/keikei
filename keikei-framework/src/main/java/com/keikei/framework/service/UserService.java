package com.keikei.framework.service;

import com.keikei.common.domain.entity.SysUser;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.common.utils.SecurityUtils;
import com.keikei.framework.manager.AsyncFactory;
import com.keikei.framework.manager.AsyncManager;
import com.keikei.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public void updateAvatarInfo(SysUser sysUser,String avatarUrl){
        if(Objects.isNull(sysUser)){
            return;
        }
        SysUser user = new SysUser();
        user.setUserId(sysUser.getUserId());
        user.setAvatar(avatarUrl);
        AsyncManager.me().execute(AsyncFactory.updateUser(user));
    }

    public SysUser getUserById(Long useId) {
         return sysUserMapper.selectUserById(useId);
    }
}
