package com.keikei.web.controller;

import com.keikei.common.domain.AjaxResult;
import com.keikei.common.domain.entity.SysMenu;
import com.keikei.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/list")
    public AjaxResult menuList(){
        List<SysMenu> list = sysMenuService.selectMenuList(new SysMenu());
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("paths",sysMenuService.transToTree(list));
        return ajaxResult;
    }
}
