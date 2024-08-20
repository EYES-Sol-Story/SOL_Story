package com.eyes.solstory.domain.user.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

	// 사용자 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", precision = 10)
    private int userNo;

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
    
    // 사용자 성별(MALE/FEMALE)
    @Column(name = "gender", nullable = false, length = 254)
    private String gender;
    
    // 사용자 생년월일
    @Column(name = "birth", nullable = false, length = 254)
    private LocalDate birth;

    // 사용자 가입일자
    @Temporal(TemporalType.DATE)
    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;

    // 캐릭터 이미지 파일 저장된 경로
    @Column(name = "character_img_path")
    private String characterImgPath;

}