package com.keikei.system.service.impl;

import com.keikei.common.domain.entity.SysMenu;
import com.keikei.common.domain.model.MenuTree;
import com.keikei.system.mapper.SysMenuMapper;
import com.keikei.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> selectMenuList(SysMenu sysMenu) {
        return sysMenuMapper.selectMenuList(sysMenu);
    }

    @Override
    public List<MenuTree> transToTree(List<SysMenu> list) {
        List<SysMenu> sysMenuTree = buildSysMenuTree(list);
        List<MenuTree> menuTrees = sysMenuTree.stream().map(MenuTree::new).collect(Collectors.toList());
        return menuTrees;
    }

    private List<SysMenu> buildSysMenuTree(List<SysMenu> list) {
        List<Long> menuIds = null;
        List<SysMenu> parentMenus = new ArrayList<>();
        if(list.size()>0){
            menuIds = list.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
            for(SysMenu sysMenu : list){
                if(!menuIds.contains(sysMenu.getParentId())){
                    ergodicNode(list,sysMenu);
                    parentMenus.add(sysMenu);
                }
            }
            if(parentMenus.isEmpty()){
                parentMenus = list;
            }
        }
        return parentMenus;
    }

    /**
     * 遍历所有子节点
     * @param list 全部节点
     * @param sysMenu 顶级节点
     */
    private void ergodicNode(List<SysMenu> list, SysMenu sysMenu) {
        //获取父节点下的子节点列表
        List<SysMenu> sysMenus = getChildList(list,sysMenu);
        sysMenu.setSysMenuList(sysMenus);
        for(SysMenu menu : sysMenus){
           if(haveSonNode(menu,list)){
                ergodicNode(list,menu);
           }
        }
    }

    /**
     * 判断有无子节点
     * @param menu
     * @param list
     * @return
     */
    private boolean haveSonNode(SysMenu menu, List<SysMenu> list) {
        for(SysMenu sysMenu : list){
            if(sysMenu.getParentId()==menu.getMenuId()){
                return true;
            }
        }
        return false;
    }

    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu sysMenu) {
        List<SysMenu> sysMenus = new ArrayList<>();
        for(SysMenu menu : list){
            if(menu.getParentId()==sysMenu.getMenuId()){
                sysMenus.add(menu);
            }
        }
        return sysMenus;
    }
}
