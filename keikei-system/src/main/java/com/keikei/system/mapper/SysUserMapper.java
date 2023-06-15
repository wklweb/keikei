package com.keikei.system.mapper;

import com.keikei.common.domain.entity.SysUser;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysUserMapper {
    SysUser selectUserByName(@Param(value = "username") String username);

    void updateUser(SysUser sysUser);

    List<SysUser> selectFriendsById(@Param(value = "userId") Long userId);

    SysUser selectUserById(@Param(value = "userId") Long useId);
}
