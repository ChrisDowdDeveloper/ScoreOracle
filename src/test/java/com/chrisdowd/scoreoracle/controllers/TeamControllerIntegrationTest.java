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
import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;
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

    @Test
    public void testThatCreateTeamSuccessfullyReturnsSavedTeam() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        team.setTeamId(null);
        String teamJson = objectMapper.writeValueAsString(team);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamId").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamCity").value("New Orlean")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamName").value("Saints")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.logoUrl").value("cdowd")
        );
    }

    @Test
    public void testThatGetAllTeamsSuccessfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/teams")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllTeamsSuccessfullyReturnsListOfTeams() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        teamService.save(team);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/teams")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].teamId").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].teamCity").value("New Orlean")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].teamName").value("Saints")
        ).andExpect(
            MockMvcResultMatchers.jsonPath(".content[0].logoUrl").value("cdowd")
        );
    }

    @Test
    public void testThatGetOneTeamSuccessfullyReturnsHttp200() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        teamService.save(team);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/teams/" + team.getTeamId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetOneTeamReturnsHttpStatus404WhenSportDoesNotExist() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        teamService.save(team);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/sports/154616541651")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetTeamReturnsSportWhenSportExists() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        TeamEntity savedTeam = teamService.save(team);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/teams/" + savedTeam.getTeamId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamId").value(savedTeam.getTeamId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamCity").value("New Orlean")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamName").value("Saints")
        ).andExpect(
            MockMvcResultMatchers.jsonPath(".logoUrl").value("cdowd")
        );
    }
    
    @Test
    public void testThatFullUpdateTeamSuccessfullyReturnsHttp200() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        TeamEntity savedTeam = teamService.save(team);

        TeamDto teamDto = TestDataUtil.createTestTeamDto(null);
        String teamDtoJson = objectMapper.writeValueAsString(teamDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/teams/" + savedTeam.getTeamId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateTeamsReturnsHttpStatus404WhenNoSportExists() throws Exception {
        TeamDto team = TestDataUtil.createTestTeamDto(null);
        String teamJson = objectMapper.writeValueAsString(team);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/teams/15641651")
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingTeam() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        TeamEntity savedTeam = teamService.save(team);

        TeamDto teamDto = TestDataUtil.createTestTeamDto(null);
        teamDto.setTeamId(savedTeam.getTeamId());

        String teamDtoUpdateJson = objectMapper.writeValueAsString(teamDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/teams/" + savedTeam.getTeamId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamDtoUpdateJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamId").value(savedTeam.getTeamId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamCity").value(teamDto.getTeamCity())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamName").value(teamDto.getTeamName())
        ).andExpect(
            MockMvcResultMatchers.jsonPath(".logoUrl").value(teamDto.getLogoUrl())
        );
    }

    @Test
    public void testThatPartialUpdateExistingTeamReturnsHttp200() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        TeamEntity savedTeam = teamService.save(team);

        TeamDto teamDto = TestDataUtil.createTestTeamDto(null);
        teamDto.setTeamName("UPDATED");
        String teamDtoJson = objectMapper.writeValueAsString(teamDto);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/teams/" + savedTeam.getTeamId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingTeamReturnsUpdatedTeam() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        teamService.save(team);

        TeamDto teamDto = TestDataUtil.createTestTeamDto(null);
        teamDto.setTeamName("UPDATED");
        String teamDtoJson = objectMapper.writeValueAsString(teamDto);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/teams/" + team.getTeamId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(teamDtoJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamId").value(team.getTeamId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.teamName").value("UPDATED")
        );
    }

    @Test
    public void testThatDeleteTeamReturnsHttp204WhenDeleted() throws Exception {
        TeamEntity team = TestDataUtil.createTestTeamA(null);
        TeamEntity savedTeam = teamService.save(team);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/teams/" + savedTeam.getTeamId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
