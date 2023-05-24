package com.hut.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hut.common.dto.ResponseResult;
import com.hut.common.dto.TokenResult;
import net.sf.json.JSONObject;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "CPFhut!@#SS";

    private static final String JWT_KEY_PHONE = "phone";

    //用户身份
    private static final String JWT_KEY_IDENTITY = "identity";

    //token类型
    private static final String JWT_TOKEN_TYPE = "tokenType";

    //生成Token
    public static String generateToken(String phone, String identity,String tokenType){
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, phone);
        map.put(JWT_KEY_IDENTITY,identity);
        map.put(JWT_TOKEN_TYPE,tokenType);
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

    public static TokenResult checkToken(String token){
        boolean result = true;
        String resultString = "";
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        }catch (Exception e){
            resultString = "token invalid";
            result = false;
        }
        return null;
    }

    public static void main(String[] args) {
        String s = generateToken("18569403691", "1","access");
        System.out.println(s);
    }
}
