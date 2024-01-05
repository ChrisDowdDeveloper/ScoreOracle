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
    
    private Long sport_id;

    private String sport_name;

    private String description;

    private String logoUrl;

}
