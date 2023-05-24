package com.hut.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.hut.common.constat.TokenConstants;
import com.hut.common.dto.ResponseResult;
import com.hut.common.dto.TokenResult;
import com.hut.common.util.JwtUtils;
import com.hut.common.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        boolean result = true;
        String resultString = "";


        String token = request.getHeader("Authorization");
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        }catch (SignatureVerificationException e){
            resultString = "token sign error";
            result = false;
        }catch (TokenExpiredException e){
            resultString = "token timeout";
            result = false;
        }catch (AlgorithmMismatchException e){
            resultString = "token AlgorithmMismatchException";
            result = false;
        }catch (Exception e){
            resultString = "token invalid";
            result = false;
        }

        if(!result){
            PrintWriter out = response.getWriter();
            out.println(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }
        //如果token编译通过，则从redis中拿token进行校验，一致则通过，否则不通过
        if(Objects.isNull(tokenResult)){
            resultString = "token invalid";
            result = false;
        }else {
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

            String redisToken = redisTemplate.opsForValue().get(tokenKey);

            if(StringUtils.isBlank(redisToken)){
                resultString = "token invalid";
                result = false;
            }else {
                if(!redisToken.trim().equals(token.trim())){
                    resultString = "token invalid";
                    result = false;
                }
            }
        }
        return result;
    }

}
