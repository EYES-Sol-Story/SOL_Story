package com.eyes.solstory.domain.userinfo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.userinfo.entity.Interest;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Integer> {
	Optional<Interest> findByUserNo(int userNo);
	
}
    // JpaRepository<EntityClass, ID타입>을 상속받음
    // 기본적으로 save(), findById(), findAll(), deleteById() 등의 메소드를 자동 제공함