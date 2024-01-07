package com.chrisdowd.scoreoracle.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDto {
    
    private Long teamId;

    private String teamCity;

    private String teamName;

    private SportDto sport;

    private String logoUrl;

}
