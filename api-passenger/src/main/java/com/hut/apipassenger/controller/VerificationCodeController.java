package com.hut.apipassenger.controller;

import com.hut.apipassenger.service.VerificationCodeService;
import com.hut.common.dto.ResponseResult;
import com.hut.common.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    VerificationCodeService service;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return service.generateVerificationCode(verificationCodeDTO.getPassengerPhone());
    }


    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();
        System.out.println("手机号："+passengerPhone);
        System.out.println("验证码："+verificationCode);
        return service.checkVerificationCode(passengerPhone,verificationCode);
    }
}
