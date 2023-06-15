package com.keikei.system.service;

import com.keikei.common.domain.entity.SysMenu;
import com.keikei.common.domain.model.MenuTree;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> selectMenuList(SysMenu sysMenu);

    List<MenuTree> transToTree(List<SysMenu> list);
}
