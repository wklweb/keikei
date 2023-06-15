package com.keikei.client.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.keikei.client.config.IMClient;
import com.keikei.client.service.MessageService;
import com.keikei.common.constants.CacheConstants;
import com.keikei.common.core.exception.sevice.ServiceException;
import com.keikei.common.domain.entity.SysGroup;
import com.keikei.common.domain.entity.SysGroupMessage;
import com.keikei.common.domain.entity.SysUserGroup;
import com.keikei.common.domain.model.GroupMessage;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.common.utils.SecurityUtils;
import com.keikei.common.utils.SpringUtils;
import com.keikei.framework.manager.AsyncFactory;
import com.keikei.framework.manager.AsyncManager;
import com.keikei.system.service.SysGroupMessageService;
import com.keikei.system.service.SysGroupService;
import com.keikei.system.service.SysUserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupMessageServiceImpl implements MessageService<GroupMessage> {
    @Autowired
    private SysGroupService groupService;
    @Autowired
    private SysUserGroupService sysUserGroupService;
    @Autowired
    private SysGroupMessageService sysGroupMessageService;
    @Autowired
    private IMClient imClient;

    @Override
    public void sendMessage(GroupMessage message) {
        SysGroup sysGroup = groupService.selectGroupById(message.getGroupId());
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjUtil.isNull(sysGroup)) {
            throw new ServiceException("该群不存在");
        } else if (!checkJoinGroup(message.getGroupId(), loginUser.getUserId())) {
            throw new ServiceException("您不在此群");
        }
        List<SysUserGroup> sysUserGroups = sysUserGroupService.selectMembers(message.getGroupId());
        String messageId = UUID.randomUUID().toString();
        message.setMessageId(messageId);
        List<Long> receiverId = sysUserGroups.stream().filter(sysUserGroup -> sysUserGroup.getMemberId().longValue()
                != loginUser.getUserId().longValue()).map(SysUserGroup::getMemberId).collect(Collectors.toList());

        //储存聊天记录
        SysGroupMessage sysGroupMessage = BeanUtil.copyProperties(message, SysGroupMessage.class);
        AsyncManager.me().execute(AsyncFactory.saveGroupMessage(sysGroupMessage));
        imClient.sendGroupMessage(receiverId, new GroupMessage[]{message});
    }

    private boolean checkJoinGroup(Long groupId, Long userId) {
        SysUserGroup sysUserGroup = sysUserGroupService.checkJoinGroup(groupId, userId);
        if (ObjUtil.isNull(sysUserGroup)) {
            return false;
        }
        return true;
    }
}
