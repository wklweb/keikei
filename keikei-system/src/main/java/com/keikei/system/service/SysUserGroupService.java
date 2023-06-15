package com.keikei.system.service;

import com.keikei.common.domain.entity.SysUserGroup;

import java.util.List;

public interface SysUserGroupService {
    SysUserGroup checkJoinGroup(Long groupId, Long userId);

    List<SysUserGroup> selectMembers(Long groupId);
}
