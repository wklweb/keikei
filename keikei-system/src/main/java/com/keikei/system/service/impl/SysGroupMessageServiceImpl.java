package com.keikei.system.service.impl;

import com.keikei.common.domain.entity.SysGroupMessage;
import com.keikei.system.mapper.SysGroupMessageMapper;
import com.keikei.system.service.SysGroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysGroupMessageServiceImpl implements SysGroupMessageService {
    @Autowired
    private SysGroupMessageMapper sysGroupMessageMapper;
    @Override
    public void saveGroupMessage(SysGroupMessage groupMessage) {
        sysGroupMessageMapper.insertGroupMessage(groupMessage);
    }

    @Override
    public void updateGroupMessage(SysGroupMessage sysGroupMessage) {
        sysGroupMessageMapper.updateGroupMessage(sysGroupMessage);
    }


}
