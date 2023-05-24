package com.hut.apipassenger.controller;

import com.hut.common.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test api passenger";
    }


    @GetMapping("/authToken")
    public ResponseResult authToken(){
        return ResponseResult.success("auth token");
    }

    @GetMapping("/noAuthToken")
    public ResponseResult noAuthToken(){
        return ResponseResult.success("noAuthToken");
    }
}
