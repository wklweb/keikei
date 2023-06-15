package com.keikei.common.core.enums;

public enum IMInfoType {
    LOGIN(0, "登陆"),
    HEART_BEAT(1, "心跳"),
    FORCE_LOGUT(2, "强制下线"),
    PRIVATE_MESSAGE(3, "私聊消息"),

    GROUP_MESSAGE(4, "群发消息"),
    All(5,"全部");
    private Integer code;

    private String desc;

    IMInfoType(Integer index, String desc) {
        this.code = index;
        this.desc = desc;
    }

    public static IMInfoType fromCode(Integer messageType) {
        for(IMInfoType imInfoType:values()){
            if(imInfoType.code==messageType){
                return imInfoType;
            }
        }
        return null;
    }

    public String description() {
        return desc;
    }

    public Integer code() {
        return this.code;
    }

}
