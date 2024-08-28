package com.eyes.solstory;

import com.eyes.solstory.domain.challenge.ChallengeDataInitializer;
import com.eyes.solstory.domain.challenge.entity.Challenge;
import com.eyes.solstory.domain.challenge.service.ChallengeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ChallengeServiceTest {

    @InjectMocks
    private ChallengeService challengeService;

    @Mock
    private ChallengeDataInitializer challengeDataInitializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRandomSavingChallenges() {
        //given
        List<Challenge> mockSavingChallenges = Arrays.asList(
                new Challenge(1, "", 30, "이번 달 5만원 더 저축하기", 3,3),
                new Challenge(1, "", 7, "이번 주 3만원 더 저축하기", 5,5),
                new Challenge(1, "", 30, "이번 달 7만원 더 저축하기", 4,4),
                new Challenge(1, "", 30, "이번 달 10만원 더 저축하기", 6,6)
        );

        when(challengeDataInitializer.getSavingChallenges()).thenReturn(mockSavingChallenges);

        // when
        List<Challenge> savingChallenges = challengeService.getRandomSavingChallenges(4);

        // then
        System.out.println("Saving Challenges: " + savingChallenges);
        assertEquals(4, savingChallenges.size()); // 저축 챌린지 4개
        assertEquals(mockSavingChallenges, savingChallenges); // 결과가 예상한 값과 동일한지 확인
    }

    @Test
    public void testGetRandomSpendingChallenges() {
        // given
        List<Challenge> mockSpendingChallenges = Arrays.asList(
                new Challenge(2, "식비", 7, "일주일 동안 전주 대비 식비 3만원 줄이기", 4,4),
                new Challenge(2, "식비", 30, "한 달 동안 전달 대비 식비 7만원 줄이기", 6,6),
                new Challenge(2, "배달음식", 7, "일주일 동안 배달 음식 먹지 않기", 4,4),
                new Challenge(2, "배달음식", 30, "한 달 동안 배달 음식 횟수 2회로 줄이기", 4,4),
                new Challenge(2, "배달음식", 30, "한 달 동안 배달 음식 없이 생활하기", 8,8),
                new Challenge(2, "음료", 7, "일주일 동안 커피숍 방문 2회 이하로 줄이기", 4,4)
        );

        when(challengeDataInitializer.getSpendingChallenges()).thenReturn(mockSpendingChallenges);

        // when
        List<Challenge> spendingChallenges = challengeService.getRandomSpendingChallenges(6);

        // then
        System.out.println("Spending Challenges: " + spendingChallenges);
        assertEquals(6, spendingChallenges.size()); // 지출 챌린지 6개
        assertEquals(mockSpendingChallenges, spendingChallenges); // 결과가 예상한 값과 동일한지 확인
    }
}
