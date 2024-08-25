package com.eyes.solstory.domain.user.dto;

import java.time.LocalDate;

import com.eyes.solstory.util.OpenApiUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String birthdate;  //
    private String gender;
    private LocalDate joinDate;
    private String accountName;   //
    private String accountNumber; //
    
    // 모든 필드를 포함하는 생성자
    public UserDto(String id, String password, String name, String email, String phone, String birthdate, String accountName, String accountNumber, String gender) {
    	setJoinDate();
    	this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthdate = birthdate;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }

    public void setJoinDate() {
    	this.joinDate = LocalDate.now();
    }
    
    public LocalDate getJoinDate() {
    	if(this.joinDate == null) {
    		this.joinDate = LocalDate.now();
    	}
    	return this.joinDate;
    }
    
    /*
    public void setBirthdate(String birthdate) {
    	LocalDate date = LocalDate.parse(birthdate, OpenApiUtil.DATE_FORMATTER);
        this.birthdate = date;
    }
    */
    
    public LocalDate transDateFormatyyyyMMdd(String date) {
    	return LocalDate.parse(date, OpenApiUtil.DATE_FORMATTER);
    }
}
