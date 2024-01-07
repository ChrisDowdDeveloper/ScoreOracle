package com.chrisdowd.scoreoracle.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.chrisdowd.scoreoracle.domain.entities.GameEntity;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, Long>, PagingAndSortingRepository<GameEntity, Long>{
    
}
