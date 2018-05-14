package com.maple.cse308.repository;

import com.maple.cse308.entity.TvReviewCritic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;

@Repository
public interface TvReviewCriticRepository extends CrudRepository<TvReviewCritic, Integer> {

    HashSet<TvReviewCritic> findAllByTvId(int tvId);

    HashSet<TvReviewCritic> findAllByCriticId(int criticId);
}
