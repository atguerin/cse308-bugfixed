package com.maple.cse308.repository;

import com.maple.cse308.entity.MovieReviewCritic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface MovieReviewCriticRepository extends CrudRepository<MovieReviewCritic, Integer> {

    HashSet<MovieReviewCritic> findAllByMovieId(int movieId);

    HashSet<MovieReviewCritic> findAllByCriticId(int criticId);

}
