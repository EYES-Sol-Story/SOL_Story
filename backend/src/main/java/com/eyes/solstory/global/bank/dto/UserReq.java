package com.eyes.solstory.global.bank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReq {
    private String apiKey;
    private String userId;

    public UserReq(String apiKey, String userId) {
        this.apiKey = apiKey;
        this.userId = userId;
    }
}