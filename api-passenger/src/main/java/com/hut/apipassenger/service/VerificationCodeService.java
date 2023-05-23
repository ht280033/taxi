package com.hut.apipassenger.service;

import com.alibaba.fastjson.JSONObject;
import com.hut.apipassenger.remote.ServiceVerificationCodeClient;
import com.hut.common.dto.ResponseResult;
import com.hut.common.response.NumberCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationCodeService {

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    public ResponseResult generateVerificationCode(String passengerPhone){

        System.out.println("调用验证码服务");

        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        System.out.println("numberCode:"+numberCode);
        //存入Redis
        System.out.println("存入redis");

        JSONObject result = new JSONObject();
        result.put("code", numberCodeResponse.getData().getNumberCode());
        result.put("message","sucess");
        return ResponseResult.success(numberCode);
    }
}
