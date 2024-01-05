package com.chrisdowd.scoreoracle.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Creates and configures a ModelMapper bean within a Spring application allowing mapping between classes if thier property names aren't fully aligned.
 * Managed by Spring and can be injected into components that require mapping functionality.
*/

@Configuration
public class MapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
