package com.keikei.client.chat.controller;

import com.keikei.client.service.Impl.PrivateMessageServiceImpl;
import com.keikei.common.domain.AjaxResult;
import com.keikei.common.domain.model.PrivateMessage;
import com.keikei.common.domain.model.TableInfo;
import com.keikei.common.utils.PageUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message/private")
public class PrivateMessageController{

    @Autowired
    private PrivateMessageServiceImpl privateMessageService;

    @PostMapping("/send")
    public AjaxResult send(@RequestBody @Validated PrivateMessage privateMessage){
        AjaxResult ajaxResult = AjaxResult.success();
        privateMessageService.sendMessage(privateMessage);
        return ajaxResult;
    }

    @PostMapping("/recall/{id}")
    public AjaxResult recall(@NotNull(value = "信息id不能为空") @PathVariable String id){
        privateMessageService.recallMessage(id);
        return AjaxResult.success();
    }

    @PostMapping("/history/{id}")
    public TableInfo getHistory(@NotNull(value = "对方id不能为空") @PathVariable Long id){
        List<PrivateMessage> privateMessages = privateMessageService.getHistory(id);
        return PageUtils.getTableInfo(privateMessages);
    }


}
