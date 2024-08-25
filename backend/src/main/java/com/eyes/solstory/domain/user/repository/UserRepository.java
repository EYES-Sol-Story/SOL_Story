package com.eyes.solstory.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findUserByUserId(@Param("userId")String userId);

	User findUserByEmail(@Param("email") String email);

	Optional<User> findUserByUserNo(@Param("userNo") int userNo);
	
	@Query("UPDATE User SET mbti = :mbti WHERE userNo = :userNo")
	int updateUserByMbti(@Param("mbti") String mbti, @Param("userNo") int userNo);
	
	@Query("UPDATE User SET characterImgPath = :imgPath WHERE userNo = :userNo")
	int updateUserByCharacter(@Param("imgPath") String imgPath, @Param("userNo") int userNo);
	
	@Query("UPDATE User SET mbti = :mbti,  characterImgPath = :imgPath WHERE userNo = :userNo")
	int updateUserInfo(@Param("mbti") String mbti, @Param("imgPath") String imgPath, @Param("userNo") int userNo);
	
	
	
	//gabin
	//계정 찾기 - 해당 이메일을 가진 회원이 존재하는지 확인 - 위와 중복으로 제거
	//
	@Query("SELECT u.userId FROM User u WHERE u.email = :email")
	String findIdByEmail(@Param("email") String email);
	
	//로그인하기 - 로그인할 때, user_no랑 user_name을 메인화면에 전달하기
	@Query("SELECT u FROM User u WHERE u.userId = :userId AND u.password = :password")
	User login(@Param("userId") String userId, @Param("password") String password); 
	
	//아이디 중복확인
	@Query("SELECT u.userId FROM User u WHERE u.userId = :userId")
    String existsByUserId(@Param("userId") String userId);
}
