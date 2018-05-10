package com.maple.cse308.repository;

import com.maple.cse308.entity.Season;
import com.maple.cse308.entity.SeasonIdentity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends CrudRepository<Season, SeasonIdentity> {

    Season save(Season season);
}
