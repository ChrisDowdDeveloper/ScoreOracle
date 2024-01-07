package com.chrisdowd.scoreoracle.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;

@Repository
public interface TeamRepository extends CrudRepository<TeamEntity, Long>, PagingAndSortingRepository<TeamEntity, Long>{

    List<TeamEntity> findBySport_SportId(Long sportId);

}
