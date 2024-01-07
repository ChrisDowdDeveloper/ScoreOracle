package com.chrisdowd.scoreoracle.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.chrisdowd.scoreoracle.TestDataUtil;
import com.chrisdowd.scoreoracle.domain.entities.GameEntity;
import com.chrisdowd.scoreoracle.domain.entities.SportEntity;
import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class GameIntegrationTest {
    
    private final GameRepository underTest;

    @Autowired
    public GameIntegrationTest(GameRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatGameCanBeCreatedAndRecalled() {
        SportEntity sport = TestDataUtil.createTestSportA();
        TeamEntity teamA = TestDataUtil.createTestTeamA(sport);
        TeamEntity teamB = TestDataUtil.createTestTeamB(sport);
        GameEntity game = TestDataUtil.createTestGameA(teamA, teamB, sport);
        underTest.save(game);
        Optional<GameEntity> result = underTest.findById(game.getGameId());
        assertTrue(result.isPresent());
        assertEquals(game, result.get());
    }

    @Test
    public void testThatMultipleGamesCanBeCreatedAndRecalled() {
        SportEntity sport = TestDataUtil.createTestSportA();
        TeamEntity teamA = TestDataUtil.createTestTeamA(sport);
        TeamEntity teamB = TestDataUtil.createTestTeamB(sport);
        GameEntity gameA = TestDataUtil.createTestGameA(teamA, teamB, sport);
        underTest.save(gameA);

        TeamEntity teamC = TestDataUtil.createTestTeamC(sport);
        TeamEntity teamD = TestDataUtil.createTestTeamB(sport);
        GameEntity gameB = TestDataUtil.createTestGameB(teamC, teamD, sport);
        underTest.save(gameB);

        TeamEntity teamE = TestDataUtil.createTestTeamC(sport);
        TeamEntity teamF = TestDataUtil.createTestTeamA(sport);
        GameEntity gameC = TestDataUtil.createTestGameC(teamE, teamF, sport);
        underTest.save(gameC);

        Iterable<GameEntity> result = underTest.findAll();
        List<GameEntity> resultList = new ArrayList<>();
        result.forEach(resultList::add);

        assertEquals(3, resultList.size());
        List<GameEntity> expectedGames = Arrays.asList(gameA, gameB, gameC);
        assertEquals(expectedGames, resultList);
    }

}
