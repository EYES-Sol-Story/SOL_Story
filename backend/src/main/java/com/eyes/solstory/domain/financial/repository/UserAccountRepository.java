package com.eyes.solstory.domain.financial.repository;

import com.eyes.solstory.domain.financial.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

    @Query("SELECT u.user.userNo, u.accountNo FROM UserAccount u WHERE u.isActive = 1")
    List<Object[]> findActiveAccounts(); //활성화된 계좌번호 조회
}