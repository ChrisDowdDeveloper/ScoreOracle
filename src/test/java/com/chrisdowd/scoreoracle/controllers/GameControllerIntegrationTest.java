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

    @Test
    public void testThatGetAllGamesSuccessfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/games")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllGamesSuccessfullyReturnsListOfGames() throws Exception {
        GameEntity game = TestDataUtil.createTestGameA(null, null, null);
        gameService.save(game);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/games")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].homeTeamScore").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].awayTeamScore").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].status").value("not started")
        );
    }

    @Test
    public void testThatGetOneTeamSuccessfullyReturnsHttp200() throws Exception {
        GameEntity game = TestDataUtil.createTestGameA(null, null, null);
        gameService.save(game);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/games/" + game.getGameId())
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetOneTeamReturnsHttpStatus404WhenGameDoesNotExist() throws Exception {
        GameEntity game = TestDataUtil.createTestGameA(null, null, null);
        gameService.save(game);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/games/2656165159661")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetOneGameReturnsGameWhenExists() throws Exception {
        GameEntity game = TestDataUtil.createTestGameA(null, null, null);
        GameEntity savedGame = gameService.save(game);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/games/" + savedGame.getGameId())
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.homeTeamScore").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.awayTeamScore").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.status").value("not started")
        );
    }

    @Test
    public void testThatFullUpdateGameSuccessfullyReturnsHttp200() throws Exception {
        GameEntity game = TestDataUtil.createTestGameA(null, null, null);
        GameEntity savedGame = gameService.save(game);

        GameDto gameDto = TestDataUtil.createTestGameDto(null, null, null);
        String gameDtoJson = objectMapper.writeValueAsString(gameDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/games/" + savedGame.getGameId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(gameDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateGameReturnsHttpStatus404WhenGameDoesNotExist() throws Exception {
        GameDto game = TestDataUtil.createTestGameDto(null, null, null);
        String gameJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/games/51263515")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gameJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingGame() throws Exception {
        GameEntity game = TestDataUtil.createTestGameA(null, null, null);
        GameEntity savedGame = gameService.save(game);

        GameDto gameDto = TestDataUtil.createTestGameDto(null, null, null);
        gameDto.setGameId(savedGame.getGameId());

        String gameDtoUpdateJson = objectMapper.writeValueAsString(gameDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/games/" + savedGame.getGameId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(gameDtoUpdateJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.homeTeamScore").value(savedGame.getHomeTeamScore())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.awayTeamScore").value(savedGame.getAwayTeamScore())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.status").value(savedGame.getStatus())
        );
    }

    @Test
    public void testThatPartialUpdateExistingGameReturnsHttp200() throws Exception {
        GameEntity game = TestDataUtil.createTestGameA(null, null, null);
        gameService.save(game);

        GameDto gameDto = TestDataUtil.createTestGameDto(null, null, null);
        gameDto.setHomeTeamScore(56);
        String gameDtoJson = objectMapper.writeValueAsString(gameDto);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/games/" + game.getGameId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(gameDtoJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.gameId").value(game.getGameId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.homeTeamScore").value(56)
        );
    }

    @Test
    public void testThatDeleteGameReturnsHttp204WhenDeleted() throws Exception {
        GameEntity game = TestDataUtil.createTestGameA(null, null, null);
        GameEntity savedGame = gameService.save(game);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/games/" + savedGame.getGameId())
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
