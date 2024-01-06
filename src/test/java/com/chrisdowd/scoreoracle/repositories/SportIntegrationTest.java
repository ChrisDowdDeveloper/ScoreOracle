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

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class SportIntegrationTest {
    
    private final SportRepository underTest;

    @Autowired
    public SportIntegrationTest(SportRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatSportCanBeCreatedAndRecalled() {
        SportEntity sport = TestDataUtil.createTestSportA();
        underTest.save(sport);
        Optional<SportEntity> result = underTest.findById(sport.getSport_id());
        assertTrue(result.isPresent());
        assertEquals(sport, result.get());
    }

    @Test
    public void testThatMultipleSportsCanBeCreatedAndRecalled() {
        SportEntity sportA = TestDataUtil.createTestSportA();
        underTest.save(sportA);

        SportEntity sportB = TestDataUtil.createTestSportB();
        underTest.save(sportB);

        SportEntity sportC = TestDataUtil.createTestSportC();
        underTest.save(sportC);

        Iterable<SportEntity> resultIterable = underTest.findAll();
        List<SportEntity> resultList = new ArrayList<>();
        resultIterable.forEach(resultList::add);

        assertEquals(3, resultList.size());
        List<SportEntity> expectedSports = Arrays.asList(sportA, sportB, sportC);
        assertEquals(expectedSports, resultList);
    }
}
