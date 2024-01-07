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
import com.chrisdowd.scoreoracle.domain.entities.SportEntity;
import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class TeamIntegrationTest {
    
    private final TeamRepository underTest;

    @Autowired
    public TeamIntegrationTest(TeamRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatTeamCanBeCreatedAndRecalled() {
        SportEntity sport = TestDataUtil.createTestSportA();
        TeamEntity team = TestDataUtil.createTestTeamA(sport);
        underTest.save(team);
        Optional<TeamEntity> result = underTest.findById(team.getTeamId());
        assertTrue(result.isPresent());
        assertEquals(team, result.get());
    }

    @Test
    public void testThatMultipleTeamsCanBeCreatedAndRecalled() {
        SportEntity sportA = TestDataUtil.createTestSportA();
        TeamEntity teamA = TestDataUtil.createTestTeamA(sportA);
        underTest.save(teamA);

        SportEntity sportB = TestDataUtil.createTestSportB();
        TeamEntity teamB = TestDataUtil.createTestTeamA(sportB);
        underTest.save(teamB);

        SportEntity sportC = TestDataUtil.createTestSportC();
        TeamEntity teamC = TestDataUtil.createTestTeamC(sportC);
        underTest.save(teamC);

        Iterable<TeamEntity> result = underTest.findAll();
        List<TeamEntity> resultList = new ArrayList<>();
        result.forEach(resultList::add);

        assertEquals(3, resultList.size());
        List<TeamEntity> expectedTeams = Arrays.asList(teamA, teamB, teamC);
        assertEquals(expectedTeams, resultList);
    }

    @Test
    public void testThatTeamCanBeUpdated() {
        SportEntity sportA = TestDataUtil.createTestSportA();
        TeamEntity teamA = TestDataUtil.createTestTeamA(sportA);
        underTest.save(teamA);
        teamA.setTeamName("UPDATED");
        underTest.save(teamA);
        Optional<TeamEntity> result = underTest.findById(teamA.getTeamId());
        assertTrue(result.isPresent());
        assertEquals(teamA, result.get());
    }

    @Test
    public void testThatTeamCanBeDeleted() {
        SportEntity sportA = TestDataUtil.createTestSportA();
        TeamEntity teamA = TestDataUtil.createTestTeamA(sportA);
        underTest.save(teamA);
        underTest.deleteById(teamA.getTeamId());
        Optional<TeamEntity> result = underTest.findById(teamA.getTeamId());
        assertTrue(result.isEmpty());
    }
}
