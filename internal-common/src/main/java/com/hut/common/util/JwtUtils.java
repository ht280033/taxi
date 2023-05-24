package com.hut.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "CPFhut!@#SS";

    private static final String JWT_KEY = "passengerPhone";

    //生成Token
    private static String generateToken(String passengerPhone){
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY, passengerPhone);
        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date time = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();

        //整合map数据
        map.forEach(builder::withClaim);
        //整合过期时间
        builder.withExpiresAt(time);

        //生成token
        String token = builder.sign(Algorithm.HMAC256(SIGN));

        return token;
    }

    //解析Token
    public static String parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        return verify.getClaim(JWT_KEY).asString();
    }

}
