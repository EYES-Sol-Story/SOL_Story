package com.eyes.solstory.domain.user.repository;

import com.eyes.solstory.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT User FROM User WHERE userId = ?1")
	User findUserByUserId(String userId);

	@Query("SELECT u.userNo FROM User u")
	List<Integer> findAllUserNos();

	@Query("SELECT u FROM User u WHERE u.email = :email")
	User findUserByEmail(@Param("email") String email);

}
