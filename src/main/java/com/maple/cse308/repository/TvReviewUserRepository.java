package com.maple.cse308.repository;

import com.maple.cse308.entity.TvReviewUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface TvReviewUserRepository extends CrudRepository<TvReviewUser, Integer> {

    HashSet<TvReviewUser> findAllByTvId(int tvId);

    HashSet<TvReviewUser> findAllByUserId(int userId);

    TvReviewUser findByReviewId(int reviewId);

    HashSet<TvReviewUser> findByUserIdAndTvId(int userId, int tvId);


}
