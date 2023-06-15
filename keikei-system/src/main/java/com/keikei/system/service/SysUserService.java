package com.keikei.system.service;

import com.keikei.common.domain.entity.SysUser;
import com.keikei.common.domain.model.Friend;

import java.util.List;

public interface SysUserService {
    SysUser selectUserByName(String username);

    void updateUser(SysUser sysUser);

    List<Friend> selectFriends(Long userId);
}
