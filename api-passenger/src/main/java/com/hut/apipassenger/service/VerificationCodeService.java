package com.hut.apipassenger.service;

import com.hut.apipassenger.remote.ServicePassengerUserClient;
import com.hut.apipassenger.remote.ServiceVerificationCodeClient;
import com.hut.common.constat.CommonStatusEnum;
import com.hut.common.dto.ResponseResult;
import com.hut.common.request.VerificationCodeDTO;
import com.hut.common.response.NumberCodeResponse;
import com.hut.common.response.TokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    private String verificationCodePrefix = "passenger-verification-code-";

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    @Autowired
    private ServicePassengerUserClient servicePassengerUserClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 生成验证码并返回
     * @param passengerPhone
     * @return
     */
    public ResponseResult generateVerificationCode(String passengerPhone){
        //调用验证码服务生成验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        //存入Redis
        //key,value,ttl
        String key = generatorKeyByPassengerPhone(passengerPhone);
        redisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        return numberCodeResponse;
    }

    /**
     * 校验验证码
     * @param passengerPhone 手机号
     * @param verificationCode 验证码
     * @return token令牌
     */
    public ResponseResult checkVerificationCode(String passengerPhone,String verificationCode){
        //根据手机号获取验证码

        //校验验证码
        String key = generatorKeyByPassengerPhone(passengerPhone);
        String codeRedis = redisTemplate.opsForValue().get(key);
        //判断原来是否存在此用户，判断是进行插入/更新
        if(StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if(!codeRedis.equals(verificationCode)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //调用远程服务判断用户是否存在
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicePassengerUserClient.getNumberCode(verificationCodeDTO);
        //办法token令牌
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token");
        return ResponseResult.success(tokenResponse);
    }

    private String generatorKeyByPassengerPhone(String passengerPhone){
        return  verificationCodePrefix+passengerPhone;
    }
}
