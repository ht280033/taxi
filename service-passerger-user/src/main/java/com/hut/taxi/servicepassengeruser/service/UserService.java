package com.hut.taxi.servicepassengeruser.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hut.common.dto.ResponseResult;
import com.hut.common.request.VerificationCodeDTO;
import com.hut.taxi.servicepassengeruser.dto.PassengerUser;
import com.hut.taxi.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private PassengerUserMapper mapper ;

    public ResponseResult loginOrReg(String passengerPhone){
        System.out.println("手机码："+passengerPhone);
        LambdaQueryWrapper<PassengerUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PassengerUser::getPassengerPhone,passengerPhone);
        List<PassengerUser> passengerUsers = mapper.selectList(queryWrapper);
        System.out.println(passengerUsers.toString());
        return ResponseResult.success();
    }
}
