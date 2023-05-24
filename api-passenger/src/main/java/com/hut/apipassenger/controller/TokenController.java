package com.hut.apipassenger.controller;

import com.hut.apipassenger.service.TokenService;
import com.hut.common.dto.ResponseResult;
import com.hut.common.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse){
        return tokenService.refreshToken(tokenResponse.getRefreshToken());
    }
}
