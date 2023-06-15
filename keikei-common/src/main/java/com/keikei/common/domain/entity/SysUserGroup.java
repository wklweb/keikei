package com.keikei.common.domain.entity;

import java.io.Serializable;

public class SysUserGroup implements Serializable {
    public static final Long serialVersionUID = 1L;
    private Long memberId;
    private Long groupId;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
