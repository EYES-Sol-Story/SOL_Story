package com.eyes.solstory.domain.financial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eyes.solstory.domain.financial.dto.ActiveAccountDTO;
import com.eyes.solstory.domain.financial.repository.UserAccountRepository;

@Service
public class UserAccountService {

	@Autowired
    private final UserAccountRepository userAccountRepository;

    public UserAccountService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public List<ActiveAccountDTO> findActiveAccounts() {
        return userAccountRepository.findActiveAccounts();
    }
}