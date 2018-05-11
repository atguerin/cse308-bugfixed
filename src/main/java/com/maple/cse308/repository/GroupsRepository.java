package com.maple.cse308.repository;

import com.maple.cse308.entity.Groups;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends CrudRepository<Groups, Integer> {

    Groups save(Groups g);

    Boolean existsByGroupName(String groupName);

    Groups findByGroupName(String groupName);

}
