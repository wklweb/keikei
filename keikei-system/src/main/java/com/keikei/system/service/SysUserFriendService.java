package com.keikei.system.service;

import com.keikei.common.domain.entity.SysUserFriend;

import java.util.List;

public interface SysUserFriendService {

    List<SysUserFriend> selectFriendList(Long userId, Long id);

    int insertNewFriend(Long userId, Long friendId);
}
