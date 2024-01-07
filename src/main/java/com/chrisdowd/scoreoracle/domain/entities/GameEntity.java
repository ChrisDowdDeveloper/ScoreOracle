package com.chrisdowd.scoreoracle.domain.entities;

import java.util.Date;

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
@Table(name = "games")
public class GameEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_id_seq")
    private Long gameId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "homeTeamId")
    private TeamEntity homeTeam;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "awayTeamId")
    private TeamEntity awayTeam;

    private Date gameDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sportId")
    private SportEntity sport;

    private Integer homeTeamScore;

    private Integer awayTeamScore;

    private String status;
    
}
