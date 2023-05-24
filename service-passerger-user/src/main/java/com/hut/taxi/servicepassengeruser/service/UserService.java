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

import java.time.LocalDateTime;
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
        //判断用户是否存在
        if(passengerUsers.isEmpty()){
            //不存在插入用户
            PassengerUser user = new PassengerUser();
            user.setPassengerName("张三");
            user.setPassengerGender(0);
            LocalDateTime now = LocalDateTime.now();
            user.setGmtCreate(now);
            user.setGmtModified(now);
            mapper.insert(user);
        }
        return ResponseResult.success();
    }
}
