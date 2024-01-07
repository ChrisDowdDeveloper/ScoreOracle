package com.chrisdowd.scoreoracle.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDto {

    private Long gameId;

    private TeamDto homeTeam;

    private TeamDto awayTeam;

    private Date gameDate;

    private SportDto sport;

    private Integer homeTeamScore;

    private Integer awayTeamScore;

    private String status;

}
