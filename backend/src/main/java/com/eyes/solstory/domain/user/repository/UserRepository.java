package com.eyes.solstory.domain.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.eyes.solstory.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findUserByUserId(String userId);

	@Query("SELECT u.userNo FROM User u")
	List<Integer> findAllUserNos();

	User findUserByEmail(String email);

}
