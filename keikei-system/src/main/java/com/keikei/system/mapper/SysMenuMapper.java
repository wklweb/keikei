package com.keikei.system.mapper;

import com.keikei.common.domain.entity.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    List<SysMenu> selectMenuList(SysMenu sysMenu);
}
