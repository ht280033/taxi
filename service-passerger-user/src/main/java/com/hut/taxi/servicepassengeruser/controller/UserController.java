package com.hut.taxi.servicepassengeruser.controller;

import com.hut.common.dto.ResponseResult;
import com.hut.common.request.VerificationCodeDTO;
import com.hut.taxi.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/users")
    public ResponseResult loginOrReg(@RequestBody VerificationCodeDTO verificationCodeDTO){
        return service.loginOrReg(verificationCodeDTO.getPassengerPhone());
    }
}
