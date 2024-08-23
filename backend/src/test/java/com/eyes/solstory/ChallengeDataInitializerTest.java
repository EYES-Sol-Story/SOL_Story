package com.eyes.solstory;

import com.eyes.solstory.domain.challenge.ChallengeDataInitializer;
import com.eyes.solstory.domain.challenge.entity.Challenge;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@SpringJUnitConfig
public class ChallengeDataInitializerTest {

    @Autowired
    private ChallengeDataInitializer challengeDataInitializer;

    @Test
    public void testInitializeChallengeData() {
        // 데이터 초기화 확인
        List<Challenge> challenges = challengeDataInitializer.getChallenges();

        assertNotNull(challenges, "Challenges 리스트는 null이 아니어야 합니다.");
        assertEquals(1, challenges.get(0).getChallengeType(), "첫 번째 챌린지의 타입은 1이어야 합니다.");
        assertEquals("", challenges.get(0).getCategory(), "첫 번째 챌린지의 카테고리는 ''이어야 합니다.");
        assertEquals(30, challenges.get(0).getDays(), "첫 번째 챌린지의 기간은 30이어야 합니다.");
        assertEquals("이번 달 5만원 더 저축하기", challenges.get(0).getChallengeName(), "첫 번째 챌린지의 이름은 '이번 달 5만원 더 저축하기'이어야 합니다.");
    }
}