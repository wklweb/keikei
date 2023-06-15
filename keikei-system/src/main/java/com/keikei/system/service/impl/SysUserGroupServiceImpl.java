package com.keikei.system.service.impl;

import com.keikei.common.domain.entity.SysUserGroup;
import com.keikei.system.mapper.SysUserGroupMapper;
import com.keikei.system.service.SysUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserGroupServiceImpl implements SysUserGroupService {
    @Autowired
    private SysUserGroupMapper sysUserGroupMapper;
    @Override
    public SysUserGroup checkJoinGroup(Long groupId, Long userId) {
        return sysUserGroupMapper.selectUserGroup(groupId,userId);
    }

    @Override
    public List<SysUserGroup> selectMembers(Long groupId) {
        return sysUserGroupMapper.selectMembersById(groupId);
    }
}
