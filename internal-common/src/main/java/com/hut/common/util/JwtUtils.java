package com.hut.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "CPFhut!@#SS";


    //生成Token
    private static String generateToken(Map<String, String> map){
        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date time = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();

        //整合map数据
        map.forEach((key, value) -> builder.withClaim(map.get(key), value));
        //整合过期时间
        builder.withExpiresAt(time);

        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();

        map.put("name","name123");
        map.put("age","18");
        String s = generateToken(map);
        System.out.println(s);
    }


    //解析Token
}
