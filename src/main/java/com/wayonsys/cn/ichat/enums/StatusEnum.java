package com.wayonsys.cn.ichat.enums;

public enum  StatusEnum {

    UNREAD("0", "未读"),
    READ("1", "已读");

    private String code;
    private String message;

    StatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusEnum getByCode(String code) {
        for (StatusEnum o : StatusEnum.values()) {
            if (o.getCode().equals(code)) {
                return o;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
