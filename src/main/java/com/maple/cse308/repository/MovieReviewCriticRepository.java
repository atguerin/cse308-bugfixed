package com.maple.cse308.repository;

import com.maple.cse308.entity.MovieReviewCritic;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.List;

public interface MovieReviewCriticRepository extends CrudRepository<MovieReviewCritic, Integer> {

    HashSet<MovieReviewCritic> findAllByMovieId(int movieId);

    HashSet<MovieReviewCritic> findAllByCriticId(int criticId);

}
