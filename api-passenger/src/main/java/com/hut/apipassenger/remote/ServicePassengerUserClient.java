package com.hut.apipassenger.remote;

import com.hut.common.dto.ResponseResult;
import com.hut.common.request.VerificationCodeDTO;
import com.hut.common.response.PassengerUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-passerger-user")
public interface ServicePassengerUserClient {

    @RequestMapping(method = RequestMethod.POST,value = "/users")
    ResponseResult<PassengerUserResponse> getNumberCode(@RequestBody VerificationCodeDTO verificationCodeDTO);
}
