package com.chrisdowd.scoreoracle;

import com.chrisdowd.scoreoracle.domain.dto.UserDto;
import com.chrisdowd.scoreoracle.domain.entities.UserEntity;

public class TestDataUtil {
    private TestDataUtil(){

    }

    public static UserEntity createTestUserA() {
        return UserEntity.builder()
            .username("ChrisDowdDev")
            .email("chrisdowddev@chrisdowd.com")
            .password("cd.dev")
            .build();
    }

    public static UserEntity createTestUserB() {
        return UserEntity.builder()
            .username("ChrisDev")
            .email("chrisdev@chrisdowd.com")
            .password("cd.dev")
            .build();
    }

    public static UserEntity createTestUserC() {
        return UserEntity.builder()
            .username("DeveloperChris")
            .email("chris@dowddeveloper.com")
            .password("cd.dev")
            .build();
    }

    public static UserDto createTestUserDto() {
        return UserDto.builder()
            .username("ChrisDowdDev")
            .email("chrisdowddev@chrisdowd.com")
            .password("cd.dev")
            .build();
    }
}