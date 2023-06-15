package com.keikei.framework.manager;

import cn.hutool.core.bean.BeanUtil;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.enums.MessageStatus;
import com.keikei.common.domain.entity.SysGroupMessage;
import com.keikei.common.domain.entity.SysPrivateMessage;
import com.keikei.common.domain.entity.SysUser;
import com.keikei.common.domain.model.GroupMessage;
import com.keikei.common.domain.model.PrivateMessage;
import com.keikei.common.utils.SpringUtils;
import com.keikei.system.service.SysGroupMessageService;
import com.keikei.system.service.SysPrivateMessageService;
import com.keikei.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.constant.Constable;
import java.util.Date;
import java.util.TimerTask;
import java.util.UUID;

/**
 * 异步任务工厂
 */
@Slf4j
public class AsyncFactory {
    public static TimerTask updateUser(SysUser sysUser) {
        log.info("更新用户信息...");
        return new TimerTask() {
            @Override
            public void run() {
                SysUserService sysUserService = SpringUtils.getBean(SysUserService.class);
                sysUserService.updateUser(sysUser);
                log.info("更新用户信息完成");
            }
        };
    }

    public static TimerTask savePrivateMessage(PrivateMessage message) {
        log.info("记录私信信息...");
        return new TimerTask() {
            @Override
            public void run() {
                SysPrivateMessageService sysPrivateMessageService = SpringUtils.getBean(SysPrivateMessageService.class);
                SysPrivateMessage sysPrivateMessage = new SysPrivateMessage();
                BeanUtil.copyProperties(message, sysPrivateMessage, true);
                sysPrivateMessage.setSendTime(new Date());
                sysPrivateMessage.setStatus(MessageStatus.UNREAD.getCode());
                sysPrivateMessageService.addMessage(sysPrivateMessage);
                message.setMessageId(sysPrivateMessage.getMessageId());
                log.info("记录私信信息成功");
            }
        };
    }

    public static TimerTask updatePrivateMessage(PrivateMessage privateMessage, Integer status) {
        log.info("更新聊天消息...");
        return new TimerTask() {
            @Override
            public void run() {
                String id = privateMessage.getMessageId();
                SysPrivateMessage sysPrivateMessage = new SysPrivateMessage();
                sysPrivateMessage.setStatus(status);
                sysPrivateMessage.setMessageId(id);
                SysPrivateMessageService sysPrivateMessageService = SpringUtils.getBean(SysPrivateMessageService.class);
                sysPrivateMessageService.update(sysPrivateMessage);
            }
        };
    }

    public static TimerTask saveGroupMessage(SysGroupMessage groupMessage) {
        log.info("记录群聊消息");
        return new TimerTask() {
            @Override
            public void run() {
                SysGroupMessageService sysGroupMessageService = SpringUtils.getBean(SysGroupMessageService.class);
                groupMessage.setStatus(MessageStatus.UNREAD.getCode());
                groupMessage.setSendTime(new Date());
                sysGroupMessageService.saveGroupMessage(groupMessage);
            }
        };
    }

    public static TimerTask updateGroupMessage(SysGroupMessage sysGroupMessage) {
        log.info("更新群聊消息");
        return new TimerTask() {
            @Override
            public void run() {
                SysGroupMessageService sysGroupMessageService = SpringUtils.getBean(SysGroupMessageService.class);
                sysGroupMessageService.updateGroupMessage(sysGroupMessage);
            }
        };
    }
}
