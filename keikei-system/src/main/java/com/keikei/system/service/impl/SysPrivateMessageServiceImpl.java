package com.keikei.system.service.impl;

import com.keikei.common.domain.entity.SysPrivateMessage;
import com.keikei.system.mapper.SysPrivateMessageMapper;
import com.keikei.system.service.SysPrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPrivateMessageServiceImpl implements SysPrivateMessageService {
    @Autowired
    private SysPrivateMessageMapper mapper;
    @Override
    public void addMessage(SysPrivateMessage sysPrivateMessage) {
        mapper.insertOneMessage(sysPrivateMessage);
    }

    @Override
    public void update(SysPrivateMessage sysPrivateMessage) {
        mapper.updateMessage(sysPrivateMessage);
    }

    @Override
    public SysPrivateMessage selectMessageById(String id) {
        return mapper.selectMessageById(id);
    }

    @Override
    public List<SysPrivateMessage> selectMessages(Long userId, Long id) {
        return mapper.selectMessages(userId,id);
    }

}
