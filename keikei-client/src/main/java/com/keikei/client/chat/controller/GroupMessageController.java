package com.keikei.client.chat.controller;

import com.keikei.client.service.Impl.GroupMessageServiceImpl;
import com.keikei.common.domain.AjaxResult;
import com.keikei.common.domain.model.GroupMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message/group/")
public class GroupMessageController {
    @Autowired
    private GroupMessageServiceImpl groupMessageService;


    @PostMapping("/send")
    public AjaxResult send(@RequestBody @Validated GroupMessage groupMessage){
        groupMessageService.sendMessage(groupMessage);
        return AjaxResult.success();
    }
}
