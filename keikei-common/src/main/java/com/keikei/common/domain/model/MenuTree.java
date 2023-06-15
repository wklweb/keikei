package com.keikei.common.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.keikei.common.domain.entity.SysMenu;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class MenuTree implements Serializable {
    public static final Long serialVersionUID = 1L;
    private Long menuId;
    private String menuName;
    private String path;
    private String icon;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuTree> sonTree;

    public MenuTree() {
    }

    public MenuTree(SysMenu sysMenu) {
        this.menuId = sysMenu.getMenuId();
        this.menuName = sysMenu.getMenuName();
        this.path = sysMenu.getPath();
        this.icon = sysMenu.getIcon();
        this.sonTree = sysMenu.getSysMenuList().stream().map(MenuTree::new).collect(Collectors.toList());
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuTree> getSonTree() {
        return sonTree;
    }

    public void setSonTree(List<MenuTree> sonTree) {
        this.sonTree = sonTree;
    }
}
