
package com.eyes.solstory.domain.userinfo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.userinfo.entity.UserInfo;

@Repository
public interface MBTIRepository extends JpaRepository<UserInfo, Integer> {
    
    Optional<UserInfo> findByUserNo(int userNo);

    // MBTI로 사용자 조회
    List<UserInfo> findByMbti(String mbti);
}