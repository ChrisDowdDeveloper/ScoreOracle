package com.chrisdowd.scoreoracle.controllers;

import javax.print.attribute.standard.Media;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.chrisdowd.scoreoracle.TestDataUtil;
import com.chrisdowd.scoreoracle.domain.entities.SportEntity;
import com.chrisdowd.scoreoracle.services.SportService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@AutoConfigureMockMvc
public class SportControllerIntegrationTest {
    
    private SportService sportService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public SportControllerIntegrationTest(SportService sportService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.sportService = sportService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateSportSuccessfullyReturnsHttp201Created() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        sport.setSport_id(null);
        String sportJson = objectMapper.writeValueAsString(sport);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/sports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sportJson)   
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateSportSuccessfullyReturnsSavedSport() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        sport.setSport_id(null);
        String sportJson = objectMapper.writeValueAsString(sport);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/sports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sportJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sport_id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sport_name").value("Football")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.league").value("National Football League")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("logo_url").value("cdowd")
        );
    }
}