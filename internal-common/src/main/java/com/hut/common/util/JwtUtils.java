package com.hut.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hut.common.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "CPFhut!@#SS";

    private static final String JWT_KEY_PHONE = "phone";

    private static final String JWT_KEY_IDENTITY = "identity";

    //生成Token
    public static String generateToken(String phone, String identity){
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, phone);
        map.put(JWT_KEY_IDENTITY,identity);
        //token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        Date time = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();

        //整合map数据
        map.forEach(builder::withClaim);
        //整合过期时间
        //builder.withExpiresAt(time); 用redis来做有效期

        //生成token
        return builder.sign(Algorithm.HMAC256(SIGN));
    }

    //解析Token
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        TokenResult result = new TokenResult();
        result.setPhone(verify.getClaim(JWT_KEY_PHONE).asString());
        result.setIdentity(verify.getClaim(JWT_KEY_IDENTITY).asString());
        return result;
    }

    public static void main(String[] args) {
        String s = generateToken("18569403691", "1");
        System.out.println(s);
    }
}
