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
import com.chrisdowd.scoreoracle.domain.dto.SportDto;
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
        sport.setSportId(null);
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
        sport.setSportId(null);
        String sportJson = objectMapper.writeValueAsString(sport);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/sports")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sportJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sportId").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sportName").value("Football")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.league").value("National Football League")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("logoUrl").value("cdowd")
        );
    }

    @Test
    public void testThatGetAllSportsSuccessfullyReturnsHttp200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/sports")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAllSportsSuccessfullyReturnsListOfSports() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        sportService.save(sport);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/sports")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].sportId").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].sportName").value("Football")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].league").value("National Football League")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("[0].logoUrl").value("cdowd")
        );
    }

    @Test
    public void testThatGetOneSportSuccessfullyReturnsHttp200() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        sportService.save(sport);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/sports/" + sport.getSportId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetOneSportReturnsHttpStatus404WhenSportDoesNotExist() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        sportService.save(sport);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/sports/154616541651")
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetSportReturnsSportWhenSportExists() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        SportEntity savedSport = sportService.save(sport);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/sports/" + savedSport.getSportId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sportId").value(savedSport.getSportId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sportName").value("Football")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.league").value("National Football League")
        ).andExpect(
            MockMvcResultMatchers.jsonPath(".logoUrl").value("cdowd")
        );
    }
    
    @Test
    public void testThatFullUpdateSportsSuccessfullyReturnsHttp200() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        SportEntity savedSport = sportService.save(sport);

        SportDto sportDto = TestDataUtil.createTestSportDto();
        String sportDtoJson = objectMapper.writeValueAsString(sportDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/sports/" + savedSport.getSportId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(sportDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateSportsReturnsHttpStatus404WhenNoSportExists() throws Exception {
        SportDto sport = TestDataUtil.createTestSportDto();
        String sportJson = objectMapper.writeValueAsString(sport);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/sports/15641651")
                .contentType(MediaType.APPLICATION_JSON)
                .content(sportJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingSport() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        SportEntity savedSport = sportService.save(sport);

        SportDto sportDto = TestDataUtil.createTestSportDto();
        sportDto.setSportId(savedSport.getSportId());

        String sportDtoUpdateJson = objectMapper.writeValueAsString(sportDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/sports/" + savedSport.getSportId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(sportDtoUpdateJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sportId").value(savedSport.getSportId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sportName").value(sportDto.getSportName())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.league").value(sportDto.getLeague())
        ).andExpect(
            MockMvcResultMatchers.jsonPath(".logoUrl").value(sportDto.getLogoUrl())
        );
    }

    @Test
    public void testThatPartialUpdateExistingSportReturnsHttp200() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        SportEntity savedSport = sportService.save(sport);

        SportDto sportDto = TestDataUtil.createTestSportDto();
        sportDto.setSportName("UPDATED");
        String sportDtoJson = objectMapper.writeValueAsString(sportDto);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/sports/" + savedSport.getSportId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(sportDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingSportReturnsUpdatedSport() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        SportEntity savedSport = sportService.save(sport);

        SportDto sportDto = TestDataUtil.createTestSportDto();
        sportDto.setSportName("UPDATED");
        String sportDtoJson = objectMapper.writeValueAsString(sportDto);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/sports/" + savedSport.getSportId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(sportDtoJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sportId").value(savedSport.getSportId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.sportName").value("UPDATED")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.league").value(sportDto.getLeague())
        ).andExpect(
            MockMvcResultMatchers.jsonPath(".logoUrl").value(sportDto.getLogoUrl())
        );
    }

    @Test
    public void testThatDeleteSportReturnsHttp204WhenDeleted() throws Exception {
        SportEntity sport = TestDataUtil.createTestSportA();
        SportEntity savedSport = sportService.save(sport);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/sports/" + savedSport.getSportId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}