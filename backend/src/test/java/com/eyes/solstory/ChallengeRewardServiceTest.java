package com.eyes.solstory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.eyes.solstory.domain.challenge.entity.ChallengeReward;
import com.eyes.solstory.domain.challenge.repository.ChallengeRewardRepository;
import com.eyes.solstory.domain.challenge.service.ChallengeRewardService;
import com.eyes.solstory.domain.user.entity.User;
import com.eyes.solstory.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig
public class ChallengeRewardServiceTest {

    @Mock
    private ChallengeRewardRepository challengeRewardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChallengeRewardService challengeRewardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("사용자 ID로 현재 수집한 열쇠 조회")
    public void findChallengeKey() {
        // given
        String userId = "user123";
        User mockUser = mock(User.class);
        ChallengeReward mockChallengeReward = mock(ChallengeReward.class);

        when(userRepository.findUserByUserId(anyString())).thenReturn(mockUser);
        when(mockUser.getUserNo()).thenReturn(1);

        when(challengeRewardRepository.findChallengeRewardByUserNo(anyInt())).thenReturn(mockChallengeReward);
        when(mockChallengeReward.getKeys()).thenReturn(10);

        // when
        int result = challengeRewardService.findChallengeKey(userId);
        // then
        assertEquals(10, result);
    }
}