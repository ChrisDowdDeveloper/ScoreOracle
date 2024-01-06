package com.chrisdowd.scoreoracle.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SportDto {
    
    private Long sportId;

    private String sportName;

    private String league;

    private String logoUrl;

}
