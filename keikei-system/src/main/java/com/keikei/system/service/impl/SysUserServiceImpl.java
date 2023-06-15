package com.keikei.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.keikei.common.domain.entity.SysUser;
import com.keikei.common.domain.model.Friend;
import com.keikei.system.mapper.SysUserMapper;
import com.keikei.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser selectUserByName(String username) {
        return sysUserMapper.selectUserByName(username);

    }

    @Override
    public void updateUser(SysUser sysUser) {
        sysUserMapper.updateUser(sysUser);
    }

    @Override
    public List<Friend> selectFriends(Long userId) {
        List<SysUser> sysUsers = sysUserMapper.selectFriendsById(userId);
        List<Friend> friends = new ArrayList<>();
        sysUsers.stream().forEach(sysUser -> {
            Friend friend = new Friend();
            BeanUtil.copyProperties(sysUser,friend,true);
            friends.add(friend);
        });
        return friends;
    }
}
