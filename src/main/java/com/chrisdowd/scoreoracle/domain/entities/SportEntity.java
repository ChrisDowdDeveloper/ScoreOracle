package com.chrisdowd.scoreoracle.domain.entities;

import jakarta.persistence.Column;
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
public class SportEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sport_id_seq")
    private Long sportId;

    private String sportName;

    @Column(unique = true)
    private String league;

    private String logoUrl;

}
