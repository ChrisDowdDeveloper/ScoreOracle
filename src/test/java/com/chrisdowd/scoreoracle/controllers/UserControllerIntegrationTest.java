package com.chrisdowd.scoreoracle.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.chrisdowd.scoreoracle.TestDataUtil;
import com.chrisdowd.scoreoracle.domain.dto.UserDto;
import com.chrisdowd.scoreoracle.domain.entities.UserEntity;
import com.chrisdowd.scoreoracle.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {
    
    private UserService userService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public UserControllerIntegrationTest(MockMvc mockMvc, UserService userService, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateUserSuccessfullyReturnsHttp201Created() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        user.setUserId(null);
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateUserSuccessfullyReturnsSavedUser() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        user.setUserId(null);
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.userId").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.username").value("ChrisDowdDev")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.email").value("chrisdowddev@chrisdowd.com")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.password").value("cd.dev")
        );
    }

    @Test
    public void testThatGetAllUsersSuccessfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllUsersSuccessfullyReturnsListOfUsers() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        userService.save(user);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].userId").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].username").value("ChrisDowdDev")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].email").value("chrisdowddev@chrisdowd.com")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].password").value("cd.dev")
        );
    }

    @Test
    public void testThatGetOneUserSuccessfullyReturnsHttp200() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        userService.save(user);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/" + user.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetOneUserReturnsHttpStatus404WhenNoUserExists() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        userService.save(user);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/6152965")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetUserReturnsUserWhenUserExists() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.save(user);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/" + savedUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.userId").value(savedUser.getUserId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.username").value("ChrisDowdDev")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.email").value("chrisdowddev@chrisdowd.com")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.password").value("cd.dev")
        );
    }

    @Test
    public void testThatFullUpdateUsersSuccessfullyReturnsHttp200() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.save(user);

        UserDto testUserDto = TestDataUtil.createTestUserDto();
        String userDtoJson = objectMapper.writeValueAsString(testUserDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/" + savedUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUsersReturnsHttpStatus404WhenNoUserExists() throws Exception {
        UserDto user = TestDataUtil.createTestUserDto();
        String userJson = objectMapper.writeValueAsString(user);
        
        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/156165")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingUser() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.save(user);

        UserDto userDto = TestDataUtil.createTestUserDto();
        userDto.setUserId(savedUser.getUserId());

        String userDtoUpdateJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/" + savedUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoUpdateJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.userId").value(savedUser.getUserId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.email").value(userDto.getEmail())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.password").value(userDto.getPassword())
        );
    }

    @Test
    public void testThatPartialUpdateExistingUserReturnsHttp200() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.save(user);

        UserDto userDto = TestDataUtil.createTestUserDto();
        userDto.setUsername("UPDATED");
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/users/" + savedUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJson)   
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingUserReturnsUpdatedUser() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.save(user);

        UserDto userDto = TestDataUtil.createTestUserDto();
        userDto.setUsername("UPDATED");
        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/users/" + savedUser.getUserId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userDtoJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.userId").value(savedUser.getUserId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.username").value("UPDATED")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.email").value(userDto.getEmail())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.password").value(userDto.getPassword())
        );
    }

    @Test
    public void testThatDeleteUsersReturnsHttp204ForNonExistingUser() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/users/264165135221")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteUserReturnsHttp204WhenDeleted() throws Exception {
        UserEntity user = TestDataUtil.createTestUserA();
        UserEntity savedUser = userService.save(user);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/users/" + savedUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON)      
            ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}