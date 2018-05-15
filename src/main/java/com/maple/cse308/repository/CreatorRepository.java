package com.maple.cse308.repository;

import com.maple.cse308.entity.Creator;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CreatorRepository extends CrudRepository<Creator, Integer> {
    List<Creator> findAllByCreatorId(int id);
}
