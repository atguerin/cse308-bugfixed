package com.maple.cse308.repository;

import com.maple.cse308.entity.Critic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriticRepository extends CrudRepository<Critic, Integer> {

    Critic save(Critic c);

    boolean existsByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Critic findByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);

    Critic findByFirstNameAndLastName(String firstName, String lastName);

    List<Critic> findAllByFirstNameOrMiddleNameOrLastNameIgnoreCase(String first, String middle, String last);
}
