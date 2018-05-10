package com.maple.cse308.repository;

import com.maple.cse308.entity.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {

    Genre save(Genre g);

    Genre findByGenre(String g);
}
