package com.maple.cse308.repository;

import com.maple.cse308.entity.Actor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActorRepository extends CrudRepository<Actor, Integer> {

    Actor findActorByActorId(int actor_id);

    Actor save(Actor a);

    boolean existsByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Actor findByFirstNameAndMiddleNameAndLastNameIgnoreCase(String firstName, String middleName, String lastName);

    Actor findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    Actor findByFirstNameIgnoreCase(String firstName);

    List<Actor> findAllByFirstNameOrMiddleNameOrLastNameIgnoreCase(String first, String middle, String last);
}
