package com.chrisdowd.scoreoracle.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.chrisdowd.scoreoracle.TestDataUtil;
import com.chrisdowd.scoreoracle.domain.entities.UserEntity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class UserIntegrationTest {
    
    private final UserRepository underTest;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public UserIntegrationTest(UserRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatUserCanBeCreatedAndRecalled() {
        UserEntity user = TestDataUtil.createTestUserA();
        underTest.save(user);
        Optional<UserEntity> result = underTest.findById(user.getUser_id());
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testThatMultipleUsersCanBeCreatedAndRecalled() {
        UserEntity userA = TestDataUtil.createTestUserA();
        underTest.save(userA);

        UserEntity userB = TestDataUtil.createTestUserB();
        underTest.save(userB);

        UserEntity userC = TestDataUtil.createTestUserC();
        underTest.save(userC);

        Iterable<UserEntity> resultIterable = underTest.findAll();
        List<UserEntity> resultList = new ArrayList<>();
        resultIterable.forEach(resultList::add);

        assertEquals(3, resultList.size());
        List<UserEntity> expectedUsers = Arrays.asList(userA, userB, userC);
        assertEquals(expectedUsers, resultList);
    }

    @Test
    @DirtiesContext
    public void testThatUsersCanNotHaveSameUsernameOrEmail() {
        UserEntity userA = TestDataUtil.createTestUserA();
        underTest.save(userA);
        entityManager.flush();

        UserEntity userB = TestDataUtil.createTestUserA();

        assertThrows(ConstraintViolationException.class, () -> {
            underTest.save(userB);
            entityManager.flush();
        });
    }

    @Test
    public void testThatUpdateChangesUser() {
        UserEntity user = TestDataUtil.createTestUserA();
        user.setUsername("UPDATED");
        underTest.save(user);
        Optional<UserEntity> result = underTest.findById(user.getUser_id());
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testThatDeleteRemovesUsers() {
        UserEntity user = TestDataUtil.createTestUserA();
        underTest.save(user);

        underTest.deleteById(user.getUser_id());
        Optional<UserEntity> result = underTest.findById(user.getUser_id());
        assertTrue(result.isEmpty());
    }

}