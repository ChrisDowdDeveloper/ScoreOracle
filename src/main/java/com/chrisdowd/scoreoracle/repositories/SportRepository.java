package com.chrisdowd.scoreoracle.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chrisdowd.scoreoracle.domain.entities.SportEntity;

@Repository
public interface SportRepository extends CrudRepository<SportEntity, Long>{
    
}
