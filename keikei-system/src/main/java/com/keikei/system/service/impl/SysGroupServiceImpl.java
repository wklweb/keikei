package com.keikei.system.service.impl;

import com.keikei.common.domain.entity.SysGroup;
import com.keikei.system.mapper.SysGroupMapper;
import com.keikei.system.service.SysGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysGroupServiceImpl implements SysGroupService {
    @Autowired
    private SysGroupMapper sysGroupMapper;
    @Override
    public SysGroup selectGroupById(Long groupId) {
        return sysGroupMapper.selectGroupById(groupId);
    }
}
