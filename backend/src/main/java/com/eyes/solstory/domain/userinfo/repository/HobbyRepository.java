package com.eyes.solstory.domain.userinfo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.userinfo.entity.Hobby;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
	Optional<Hobby> findByUserNo(int userNo);
}