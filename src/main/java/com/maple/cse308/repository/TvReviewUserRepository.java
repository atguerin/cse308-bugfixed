package com.maple.cse308.repository;

import com.maple.cse308.entity.TvReviewUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TvReviewUserRepository extends CrudRepository<TvReviewUser, Integer> {

    List<TvReviewUser> findAllByTvId(int tvId);

    List<TvReviewUser> findAllByUserId(int userId);
}
