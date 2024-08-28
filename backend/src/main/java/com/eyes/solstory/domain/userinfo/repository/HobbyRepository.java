package com.eyes.solstory.domain.userinfo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.userinfo.entity.Hobby;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Integer> {
	@Query("SELECT hobby FROM Hobby WHERE userNo = :userNo")
	List<String> findAllHobbyByUserNo(@Param("userNo") int userNo);
	
}