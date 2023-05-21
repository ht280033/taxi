package com.hut.apipassenger.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {
    public String generateVerificationCode(String passengerPhone){

        System.out.println("调用验证码服务");
        Integer code = 1;

        //存入Redis
        System.out.println("存入redis");

        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message","sucess");
        return result.toJSONString();
    }
}
