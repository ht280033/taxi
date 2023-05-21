package com.hut.apipassenger.controller;

import com.hut.apipassenger.request.VerificationCodeDTO;
import com.hut.apipassenger.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    VerificationCodeService service;

    @GetMapping("/verification-code")
    public String verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        System.out.println("手机号码："+verificationCodeDTO.getPassengerPhone());
        return service.generateVerificationCode(verificationCodeDTO.getPassengerPhone());
    }
}
