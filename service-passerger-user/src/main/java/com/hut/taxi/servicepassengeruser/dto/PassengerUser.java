package com.hut.taxi.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PassengerUser {

    private String id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String passengerPhone;

    private String passengerName;

    private Boolean passengerGender;

    private Boolean status;
}
