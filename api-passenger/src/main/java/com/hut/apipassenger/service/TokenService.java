package com.hut.apipassenger.service;

import com.auth0.jwt.JWT;
import com.hut.common.constat.CommonStatusEnum;
import com.hut.common.constat.TokenConstants;
import com.hut.common.dto.ResponseResult;
import com.hut.common.dto.TokenResult;
import com.hut.common.response.TokenResponse;
import com.hut.common.util.JwtUtils;
import com.hut.common.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;


    public ResponseResult refreshToken(String refreshTokenSrc) {
        //解析refreshToken
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if(Objects.isNull(tokenResult)){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();
        //读取redis中的refreshToken
        String refreshTokenKeyRedis = RedisPrefixUtils.generatorTokenKey(phone, identity,
                TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshTokenRedis = redisTemplate.opsForValue().get(refreshTokenKeyRedis);
        //比较redis中的token与传入的token
        if((StringUtils.isBlank(refreshTokenRedis)) || (!refreshTokenRedis.trim().equals(refreshTokenSrc.trim()))){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        //生成双token
        String accessToken = JwtUtils.generateToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity,
                TokenConstants.ACCESS_TOKEN_TYPE);
        redisTemplate.opsForValue().set(accessTokenKey, accessToken,30,TimeUnit.DAYS);

        String refreshToken = JwtUtils.generateToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity,
                TokenConstants.REFRESH_TOKEN_TYPE);
        redisTemplate.opsForValue().set(refreshTokenKey, refreshToken,31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
