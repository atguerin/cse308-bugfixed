package com.maple.cse308.repository;

import com.maple.cse308.entity.TvActor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TvActorRepository extends CrudRepository<TvActor, Integer> {
    public List<TvActor> findAllByTvId(int id);
}
