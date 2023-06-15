package com.keikei.system.mapper;

import com.keikei.common.domain.entity.SysGroup;
import org.springframework.data.repository.query.Param;

public interface SysGroupMapper {
    SysGroup selectGroupById(@Param(value = "groupId") Long groupId);
}
