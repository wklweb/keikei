package com.keikei.system.mapper;

import com.keikei.common.domain.entity.SysPrivateMessage;
import com.keikei.system.service.SysPrivateMessageService;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysPrivateMessageMapper {
    void insertOneMessage(SysPrivateMessage sysPrivateMessage);

    void updateMessage(SysPrivateMessage sysPrivateMessage);

    SysPrivateMessage selectMessageById(@Param(value = "messageId") String id);

    List<SysPrivateMessage> selectMessages(@Param(value = "sendId") Long sendId, @Param(value = "receiverId") Long receiverId);
}
