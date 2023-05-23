package com.hut.common.constat;

public enum CommonStatusEnum {
    //成功
    SUCCESS(1,"success"),
    //失败
    ERROR(0,"fail"),
    ;

    private int code;
    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}
