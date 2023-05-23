package com.hut.apipassenger.service;

import com.alibaba.fastjson.JSONObject;
import com.hut.apipassenger.remote.ServiceVerificationCodeClient;
import com.hut.common.dto.ResponseResult;
import com.hut.common.response.NumberCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public ResponseResult generateVerificationCode(String passengerPhone){
        //调用验证码服务生成验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        //存入Redis
        //key,value,ttl
        String key = verificationCodePrefix+passengerPhone;
        redisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        return ResponseResult.success();
    }
}
