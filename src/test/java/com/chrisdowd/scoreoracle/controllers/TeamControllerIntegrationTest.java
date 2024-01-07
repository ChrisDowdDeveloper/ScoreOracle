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
import com.chrisdowd.scoreoracle.domain.dto.TeamDto;
import com.chrisdowd.scoreoracle.services.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@AutoConfigureMockMvc
public class TeamControllerIntegrationTest {
    
    private TeamService teamService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public TeamControllerIntegrationTest(TeamService teamService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.teamService = teamService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateTeamReturnsHttpStatus201() throws Exception {
        TeamDto team = TestDataUtil.createTestTeamDto(null);
        String createTeamJson = objectMapper.writeValueAsString(team);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/teams")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createTeamJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
