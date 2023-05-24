package com.hut.common.util;

public class RedisPrefixUtils {

    public static String verificationCodePrefix = "passenger-verification-code-";

    public static String tokenPrefix = "token-";

    public static String generatorKeyByPassengerPhone(String passengerPhone) {
        return verificationCodePrefix + passengerPhone;
    }

    public static String generatorTokenKey(String phone, String identity,String tokenType) {
        return tokenPrefix + phone + "-" + identity+"-" + tokenType;
    }
}
