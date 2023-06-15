package com.keikei.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.keikei.common.constants.UserStatus;
import com.keikei.common.core.enums.IMInfoType;
import com.keikei.common.core.enums.MessageStatus;
import com.keikei.common.core.exception.sevice.ServiceException;
import com.keikei.common.domain.AjaxResult;
import com.keikei.common.domain.entity.SysUserFriend;
import com.keikei.common.domain.model.Friend;
import com.keikei.common.domain.model.LoginUser;
import com.keikei.netty.nettys.UserChannelCtxMap;
import com.keikei.system.service.SysUserFriendService;
import com.keikei.system.service.SysUserService;
import com.keikei.web.base.BaseController;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserFriendService sysUserFriendService;

    @GetMapping("/list")
    public AjaxResult getFriend(){
        LoginUser loginUser = getLoginUser();
        List<Friend> friends = sysUserService.selectFriends(loginUser.getUserId());
        friends.stream().forEach(friend -> {
            if(UserChannelCtxMap.getChannelCtx(friend.getUserId())!=null){
                friend.setIsOnline(UserStatus.Online.getCode());
            }
            else {
                friend.setIsOnline(UserStatus.noOnline.getCode());
            }
        });
        return AjaxResult.success("获取成功",friends);
    }
    @PostMapping("/add/{userId}")
    public AjaxResult addFriend(@PathVariable @NotNull Long userId){
        LoginUser loginUser = getLoginUser();
        if(userId==loginUser.getUserId()){
            throw new ServiceException("不能添加自己为好友");
        }
        List<SysUserFriend> sysUserFriends = sysUserFriendService.selectFriendList(userId,loginUser.getUserId());
        if(!sysUserFriends.isEmpty()){
           throw new ServiceException("对方已是你的朋友");
        }
        return sysUserFriendService.insertNewFriend(loginUser.getUserId(), userId)>0?AjaxResult.success("添加成功"):
                AjaxResult.error("添加失败");

    }



}
