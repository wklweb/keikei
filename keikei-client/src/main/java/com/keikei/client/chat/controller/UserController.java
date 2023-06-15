package com.keikei.client.chat.controller;

import com.keikei.client.service.OnlineService;
import com.keikei.common.domain.AjaxResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private OnlineService onlineService;

    @GetMapping("/online")
    public AjaxResult getOnline(@NotNull @RequestParam String userIds) {
        List<String> ids = onlineService.filterNotOnlineUserId(userIds);
        return AjaxResult.success("获取成功",ids);
    }
}
