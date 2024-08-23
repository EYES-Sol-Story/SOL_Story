package com.eyes.solstory.domain.user.repository;

import com.eyes.solstory.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT User FROM User WHERE userId = ?1")
	User findUserByUserId(String userId);
}
