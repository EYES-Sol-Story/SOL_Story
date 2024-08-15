package com.eyes.solstory.domain.user.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User {

	// 사용자 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo;

    // 사용자 ID
    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    // 사용자 비밀번호
    @Column(name = "password", nullable = false, length = 64)
    private String password;

    // 사용자 이름
    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    // 사용자 이메일
    @Column(name = "email", nullable = false, length = 254)
    private String email;

    // 사용자 휴대폰 번호
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    // 사용자 가입일자
    @Temporal(TemporalType.DATE)
    @Column(name = "join_date", nullable = false)
    private Date joinDate;
    
    // getter setter

}