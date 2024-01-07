package com.chrisdowd.scoreoracle.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "teams")
public class TeamEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_seq")
    private Long teamId;

    private String teamCity;

    private String teamName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sportId")
    private SportEntity sport;

    private String logoUrl;

}
