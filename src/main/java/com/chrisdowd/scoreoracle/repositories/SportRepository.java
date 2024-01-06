package com.chrisdowd.scoreoracle.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisdowd.scoreoracle.domain.entities.SportEntity;

@Repository
public interface SportRepository extends CrudRepository<SportEntity, Long>{
    
    @Query("SELECT s FROM SportEntity s WHERE s.sport_name = ?1")
    Iterable<SportEntity> findAllSportByName(String sport_name);
}
