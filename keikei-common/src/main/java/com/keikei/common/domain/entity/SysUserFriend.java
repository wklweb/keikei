package com.keikei.common.domain.entity;

import java.io.Serializable;

public class SysUserFriend implements Serializable {
    public static final Long serialVersionUID = 1L;
    private Long userId;
    private Long friendId;

    public SysUserFriend() {
    }

    public SysUserFriend(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }
}
