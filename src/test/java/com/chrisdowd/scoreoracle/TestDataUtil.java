package com.chrisdowd.scoreoracle;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.chrisdowd.scoreoracle.domain.dto.GameDto;
import com.chrisdowd.scoreoracle.domain.dto.SportDto;
import com.chrisdowd.scoreoracle.domain.dto.TeamDto;
import com.chrisdowd.scoreoracle.domain.dto.UserDto;
import com.chrisdowd.scoreoracle.domain.entities.GameEntity;
import com.chrisdowd.scoreoracle.domain.entities.SportEntity;
import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;
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

    public static SportEntity createTestSportA() {
        return SportEntity.builder()
            .sportName("Football")
            .league("National Football League")
            .logoUrl("cdowd")
            .build();
    }

    public static SportEntity createTestSportB() {
        return SportEntity.builder()
            .sportName("Hockey")
            .league("National Hockey League")
            .logoUrl("cdowd")
            .build();
    }

    public static SportEntity createTestSportC() {
        return SportEntity.builder()
            .sportName("Basketball")
            .league("National Basketball Association")
            .logoUrl("cdowd")
            .build();
    }

    public static SportDto createTestSportDto() {
        return SportDto.builder()
            .sportName("Football")
            .league("National Football League")
            .logoUrl("cdowd")
            .build();
    }

    public static TeamEntity createTestTeamA(final SportEntity sport) {
        return TeamEntity.builder()
            .teamCity("New Orlean")
            .teamName("Saints")
            .sport(sport)
            .logoUrl("cdowd")
            .build();
    }

    public static TeamEntity createTestTeamB(final SportEntity sport) {
        return TeamEntity.builder()
            .teamCity("Milwaukee")
            .teamName("Bucks")
            .sport(sport)
            .logoUrl("cdowd")
            .build();
    }

    public static TeamEntity createTestTeamC(final SportEntity sport) {
        return TeamEntity.builder()
            .teamCity("St. Louis")
            .teamName("Blues")
            .sport(sport)
            .logoUrl("cdowd")
            .build();
    }

    public static TeamDto createTestTeamDto(final SportDto sport) {
        return TeamDto.builder()
            .teamCity("Anaheim")
            .teamName("Ducks")
            .sport(sport)
            .build();
    }

    public static GameEntity createTestGameA(final TeamEntity teamA, final TeamEntity teamB, final SportEntity sport) {
        LocalDate localDate = LocalDate.of(2024, 1, 7);
        Date gameDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return GameEntity.builder()
            .homeTeam(teamA)
            .awayTeam(teamB)
            .gameDate(gameDate)
            .sport(sport)
            .homeTeamScore(0)
            .awayTeamScore(0)
            .status("not started")
            .build();
    }

    public static GameEntity createTestGameB(final TeamEntity teamA, final TeamEntity teamB, final SportEntity sport) {
        LocalDate localDate = LocalDate.of(2024, 1, 7);
        Date gameDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return GameEntity.builder()
            .homeTeam(teamA)
            .awayTeam(teamB)
            .gameDate(gameDate)
            .sport(sport)
            .homeTeamScore(0)
            .awayTeamScore(0)
            .status("not started")
            .build();
    }

    public static GameEntity createTestGameC(final TeamEntity teamA, final TeamEntity teamB, final SportEntity sport) {
        LocalDate localDate = LocalDate.of(2024, 1, 7);
        Date gameDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return GameEntity.builder()
            .homeTeam(teamA)
            .awayTeam(teamB)
            .gameDate(gameDate)
            .sport(sport)
            .homeTeamScore(0)
            .awayTeamScore(0)
            .status("not started")
            .build();
    }

    public static GameDto createTestGameDto(final TeamDto teamA, final TeamDto teamB, final SportDto sport) {
        LocalDate localDate = LocalDate.of(2024, 1, 7);
        Date gameDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return GameDto.builder()
            .homeTeam(teamA)
            .awayTeam(teamB)
            .gameDate(gameDate)
            .sport(sport)
            .homeTeamScore(0)
            .awayTeamScore(0)
            .status("not started")
            .build();
    }
}
