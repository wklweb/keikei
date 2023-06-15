package com.keikei.client.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.keikei.client.config.IMClient;
import com.keikei.client.service.MessageService;
import com.keikei.common.constants.Constants;
import com.keikei.common.core.enums.MessageStatus;
import com.keikei.common.core.enums.MessageType;
import com.keikei.common.core.exception.sevice.ServiceException;
import com.keikei.common.domain.entity.SysPrivateMessage;
import com.keikei.common.domain.entity.SysUserFriend;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.common.domain.model.PrivateMessage;
import com.keikei.common.utils.PageUtils;
import com.keikei.framework.manager.AsyncFactory;
import com.keikei.framework.manager.AsyncManager;
import com.keikei.system.service.SysPrivateMessageService;
import com.keikei.system.service.SysUserFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrivateMessageServiceImpl implements MessageService<PrivateMessage> {
    Logger logger = LoggerFactory.getLogger("sys-message");
    @Autowired
    private IMClient imClient;
    @Autowired
    private SysPrivateMessageService sysPrivateMessageService;
    @Autowired
    private SysUserFriendService sysUserFriendService;


    @Override
    public void sendMessage(PrivateMessage message) {
        String id = UUID.randomUUID().toString();
        message.setMessageId(id);
        AsyncManager.me().execute(AsyncFactory.savePrivateMessage(message));
        message.setSendTime(new Date());
        imClient.sendPrivateMessage(message);
    }

    /**
     * 撤回消息
     *
     * @param id
     */

    public void recallMessage(String id) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysPrivateMessage privateMessage = sysPrivateMessageService.selectMessageById(id);
        try {
            if (Objects.isNull(privateMessage)) {
                throw new ServiceException("撤回的消息不存在");
            } else if (privateMessage.getSendId() != loginUser.getUserId()) {
                throw new ServiceException("无法撤回他人的消息");
            } else if (System.currentTimeMillis() - privateMessage.getSendTime().getTime() > Constants.RECALL_TIME) {
                throw new ServiceException("超过五分钟无法撤回");
            } else if (privateMessage.getStatus() == MessageStatus.RECALL.getCode()) {
                throw new ServiceException("该消息已经撤回");
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw e;
        }
        PrivateMessage message = BeanUtil.copyProperties(privateMessage, PrivateMessage.class);
        message.setType(MessageType.system.getCode());
        AsyncManager.me().execute(AsyncFactory.updatePrivateMessage(message, MessageStatus.RECALL.getCode()));//数据库消息更新
        PrivateMessage sysMessage = new PrivateMessage(loginUser.getUserId(), message.getReceiverId(), "对方撤回了一条消息"
                , MessageType.system.getCode());
        imClient.sendPrivateMessage(sysMessage);
        logger.info("撤回私聊消息，发送id:{},接收id:{}，内容:{}", sysMessage.getSendId(), sysMessage.getReceiverId(),
                sysMessage.getText());
    }

    public List<PrivateMessage> getHistory(Long id) {
        try {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            checkGetHistory(loginUser.getUserId(), id);
            PageUtils.startPage();
            List<SysPrivateMessage> privateMessages = sysPrivateMessageService.selectMessages(loginUser.getUserId(), id);
            return privateMessages.stream().filter(sysPrivateMessage -> sysPrivateMessage.getType().intValue()!=
                            MessageType.system.getCode()).filter(sysPrivateMessage -> sysPrivateMessage.getStatus().intValue()
            != MessageStatus.RECALL.getCode()).map(PrivateMessage::new).collect(Collectors.toList());

        }
        catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }

    }

    private void checkGetHistory(Long userId, Long id) {
        List<SysUserFriend> sysUserFriend = sysUserFriendService.selectFriendList(userId, id);
        if (sysUserFriend.isEmpty()) {
            throw new ServiceException("对方不是您的好友");
        }
    }
}
