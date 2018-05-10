package com.maple.cse308.repository;

import com.maple.cse308.entity.MovieTrailer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieTrailerRepository extends CrudRepository<MovieTrailer, Integer> {

    List<MovieTrailer> findAllByMovieId(Integer movieId);
}
