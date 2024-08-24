package com.eyes.solstory.domain.financial.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.eyes.solstory.domain.financial.dto.ActiveAccountDTO;
import com.eyes.solstory.domain.financial.repository.UserAccountRepository;

import lombok.AllArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public List<ActiveAccountDTO> findActiveAccounts() {
        return userAccountRepository.findActiveAccounts();
    }
}