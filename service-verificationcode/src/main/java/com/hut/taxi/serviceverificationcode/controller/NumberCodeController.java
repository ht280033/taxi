package com.hut.taxi.serviceverificationcode.controller;

import com.alibaba.fastjson.JSONObject;
import com.hut.common.dto.ResponseResult;
import com.hut.common.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {
    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        //生成验证码
        double mathRandom = (Math.random()*9+1) * (Math.pow(10,size-1));
        int numberCode = (int)mathRandom;
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(numberCode);
        return ResponseResult.success(response);
    }
}
