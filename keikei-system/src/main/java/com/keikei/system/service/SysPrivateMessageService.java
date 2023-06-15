package com.keikei.system.service;

import com.keikei.common.domain.entity.SysPrivateMessage;

import java.util.List;

public interface SysPrivateMessageService {
    void addMessage(SysPrivateMessage sysPrivateMessage);

    void update(SysPrivateMessage sysPrivateMessage);

    SysPrivateMessage selectMessageById(String id);

    List<SysPrivateMessage> selectMessages(Long userId, Long id);
}
