package com.hut.common.constat;

public enum CommonStatusEnum {

    //验证码不正确
    VERIFICATION_CODE_ERROR(1099,"验证码不正确或已过期"),

    //token类提示
    TOKEN_ERROR(1199,"token错误"),

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
