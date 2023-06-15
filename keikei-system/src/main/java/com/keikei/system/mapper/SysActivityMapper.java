package com.keikei.system.mapper;

import com.keikei.common.domain.entity.SysActivity;

import java.util.List;

public interface SysActivityMapper
{
    List<SysActivity> selectActivityList(SysActivity sysActivity);

}
