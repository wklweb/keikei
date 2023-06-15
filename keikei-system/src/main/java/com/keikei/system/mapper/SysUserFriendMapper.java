package com.keikei.system.mapper;

import com.keikei.common.domain.entity.SysUserFriend;

import java.util.List;

public interface SysUserFriendMapper {
    List<SysUserFriend> selectUserFriendList(SysUserFriend sysUserFriend);

    int insertUserFriend(SysUserFriend sysUserFriend);
}
