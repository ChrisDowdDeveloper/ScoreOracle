package com.chrisdowd.scoreoracle.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sports")
public class SportsEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sport_id_seq")
    private Long sport_id;

    private String sport_name;

    private String description;

    private String logoUrl;

}
