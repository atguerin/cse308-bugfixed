package com.maple.cse308.repository;

import com.maple.cse308.entity.MovieReviewUser;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.List;

public interface MovieReviewUserRepository extends CrudRepository<MovieReviewUser, Integer> {

    HashSet<MovieReviewUser> findAllByMovieId(int movieId);

    HashSet<MovieReviewUser> findAllByUserId(int userId);

    List<MovieReviewUser> findAllByUserIdAndMovieId(int userId, int movieId);

    void delete(MovieReviewUser mru);
    MovieReviewUser findByReviewId(Integer reviewId);

}
