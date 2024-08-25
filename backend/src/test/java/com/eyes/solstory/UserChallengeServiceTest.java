package com.eyes.solstory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.eyes.solstory.domain.user.entity.User;
import com.eyes.solstory.global.exception.UserChallengeNotFoundException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eyes.solstory.domain.challenge.entity.Challenge;
import com.eyes.solstory.domain.challenge.entity.UserChallenge;
import com.eyes.solstory.domain.challenge.repository.UserChallengeRepository;
import com.eyes.solstory.domain.challenge.service.UserChallengeService;

public class UserChallengeServiceTest {

    @Mock
    private UserChallengeRepository userChallengeRepository;

    @InjectMocks
    private UserChallengeService userChallengeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("기간 내에 있는 챌린지가 있는 경우")
    void valid() {
        // given
        int userNo = 1;
        LocalDate assignedDate = LocalDate.of(2024, 8, 23);
        Challenge challenge = new Challenge(1, "", 7, "이번 주 3만원 더 저축하기", 5);
        UserChallenge userChallenge = UserChallenge.builder()
                .assignmentNo(1)
                .user(new User()) // 적절한 User 객체를 설정
                .challenge(challenge)
                .assignedDate(assignedDate)
                .completeDate(null)
                .build();

        when(userChallengeRepository.findByUser_UserNo(userNo)).thenReturn(userChallenge);

        // when
        LocalDate today = LocalDate.of(2024, 8, 25); // 테스트 시점의 날짜 설정
        UserChallenge result = userChallengeService.getUserChallengesStatus(userNo);

        // then
        assertNotNull(result);
        assertEquals(userChallenge, result);
    }

    @Test
    @DisplayName("챌린지가 없는 경우 UserChallengeNotFoundException 에러 반환")
    void noChallenge() {
        // given
        int userNo = 1;
        when(userChallengeRepository.findByUser_UserNo(userNo)).thenReturn(null);

        // when
        LocalDate today = LocalDate.of(2024, 8, 25);

        // then
        UserChallengeNotFoundException thrown = assertThrows(UserChallengeNotFoundException.class, () ->
                userChallengeService.getUserChallengesStatus(userNo));
        assertEquals("해당하는 챌린지가 없습니다.", thrown.getMessage());
    }

    @Test
    @DisplayName("챌린지 범위를 벗어난 날짜 설정 시 null 반환")
    void outsideDateRange() {
        // given
        int userNo = 1;
        LocalDate assignedDate = LocalDate.of(2024, 5, 23);
        Challenge challenge = new Challenge(1, "", 7, "이번 주 3만원 더 저축하기", 5);
         UserChallenge userChallenge = UserChallenge.builder()
                .assignmentNo(1)
                .user(new User())
                .challenge(challenge)
                .assignedDate(assignedDate)
                .completeDate(null)
                .build();

        when(userChallengeRepository.findByUser_UserNo(userNo)).thenReturn(userChallenge);

        // when
        UserChallenge result = userChallengeService.getUserChallengesStatus(userNo);

        // then
        assertNull(result);
    }
}

