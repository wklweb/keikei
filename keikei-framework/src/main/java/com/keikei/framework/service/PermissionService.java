package com.keikei.framework.service;

import com.keikei.common.domain.entity.SysUser;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PermissionService {

    public Set<String> setPermission(SysUser sysUser) {
        Set<String> strings = new HashSet<>();
        if(sysUser.isAdmin()){
            strings.add("*:*:*");
        }
        else {
            strings.add("*:*:*");
        }
        return strings;
    }
}
