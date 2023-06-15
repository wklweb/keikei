package com.keikei.system.mapper;

import com.keikei.common.domain.entity.SysUserGroup;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysUserGroupMapper {
    SysUserGroup selectUserGroup(@Param(value = "groupId") Long groupId,@Param(value = "userId") Long userId);

    List<SysUserGroup> selectMembersById(@Param(value = "groupId") Long groupId);
}
