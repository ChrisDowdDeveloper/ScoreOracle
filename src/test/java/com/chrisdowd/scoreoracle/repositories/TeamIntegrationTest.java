package com.chrisdowd.scoreoracle.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
