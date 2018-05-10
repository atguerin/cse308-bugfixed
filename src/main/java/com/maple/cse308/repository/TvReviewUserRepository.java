package com.maple.cse308.repository;

import com.maple.cse308.entity.TvReviewUser;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;
import java.util.List;

public interface TvReviewUserRepository extends CrudRepository<TvReviewUser, Integer> {

    HashSet<TvReviewUser> findAllByTvId(int tvId);

    HashSet<TvReviewUser> findAllByUserId(int userId);
}
