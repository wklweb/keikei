package com.keikei.system.service;

import com.keikei.common.domain.entity.SysGroupMessage;

public interface SysGroupMessageService {
    void saveGroupMessage(SysGroupMessage groupMessage);

    void updateGroupMessage(SysGroupMessage sysGroupMessage);
}
