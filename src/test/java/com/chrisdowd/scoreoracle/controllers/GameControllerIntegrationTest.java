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
import com.chrisdowd.scoreoracle.domain.dto.GameDto;
import com.chrisdowd.scoreoracle.domain.entities.GameEntity;
import com.chrisdowd.scoreoracle.services.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@AutoConfigureMockMvc
public class GameControllerIntegrationTest {
    
    private GameService gameService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public GameControllerIntegrationTest(ObjectMapper objectMapper, MockMvc mockMvc, GameService gameService) {
        this.gameService = gameService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateGameReturnsHttpStatus201() throws Exception {
        GameDto game = TestDataUtil.createTestGameDto(null, null, null);
        String createGameJson = objectMapper.writeValueAsString(game);
        
        mockMvc.perform(
            MockMvcRequestBuilders.post("/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(createGameJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCreateGameSuccessfullyReturnsSavedGame() throws Exception {
        GameEntity game = TestDataUtil.createTestGameA(null, null, null);
        game.setGameId(null);
        String gameJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gameJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.homeTeamScore").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.awayTeamScore").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.status").value("not started")
        );
    }

}
