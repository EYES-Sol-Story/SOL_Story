package com.eyes.solstory.global.bank.dto;

import com.eyes.solstory.global.bank.dto.Header;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SavingsAccountReq {
    private Header header;
    private String accountTypeUniqueNo;

    private String withdrawalAccountNo;
    private long depositBalance;

    @Override
    public String toString() {
        return "SavingsAccountReq{" +
                "header=" + header +
                ", accountTypeUniqueNo='" + accountTypeUniqueNo + '\'' +
                ", withdrawalAccountNo='" + withdrawalAccountNo + '\'' +
                ", depositBalance=" + depositBalance +
                '}';
    }
}
