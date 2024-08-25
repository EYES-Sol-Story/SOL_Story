package com.eyes.solstory.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findUserByUserId(String userId);

	User findUserByEmail(String email);

	Optional<User> findUserByUserNo(int userNo);
	
	@Query("UPDATE User SET mbti = :mbti WHERE userNo = :userNo")
	int updateUserByMbti(String mbti, int userNo);
	
	@Query("UPDATE User SET characterImgPath = :imgPath WHERE userNo = :userNo")
	int updateUserByCharacter(String imgPath, int userNo);
	
	@Query("UPDATE User SET mbti = :mbti,  characterImgPath = :iImgPath WHERE userNo = :userNo")
	int updateUserInfo(String mbti, String imgPath, int userNo);
}
