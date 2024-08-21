package com.eyes.solstory.domain.financial.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveAccountDTO {
    private int userNo;
    private String userKey;
    private String accountNo;
    private int accountType;
}