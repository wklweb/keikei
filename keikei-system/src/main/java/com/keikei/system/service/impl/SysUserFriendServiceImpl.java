package com.keikei.system.service.impl;

import com.keikei.common.domain.entity.SysUserFriend;
import com.keikei.system.mapper.SysUserFriendMapper;
import com.keikei.system.service.SysUserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserFriendServiceImpl implements SysUserFriendService {
    @Autowired
    private SysUserFriendMapper sysUserFriendMapper;


    @Override
    public List<SysUserFriend> selectFriendList(Long userId, Long id) {
        SysUserFriend sysUserFriend = new SysUserFriend(userId,id);
        return sysUserFriendMapper.selectUserFriendList(sysUserFriend);
    }

    @Override
    public int insertNewFriend(Long userId, Long friendId) {
        SysUserFriend sysUserFriend = new SysUserFriend(userId,friendId);
        return sysUserFriendMapper.insertUserFriend(sysUserFriend);
    }
}
