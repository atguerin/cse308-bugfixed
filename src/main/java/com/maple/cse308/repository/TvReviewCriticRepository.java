package com.maple.cse308.repository;

import com.maple.cse308.entity.TvReviewCritic;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;

public interface TvReviewCriticRepository extends CrudRepository<TvReviewCritic, Integer> {

   HashSet<TvReviewCritic> findAllByTvId(int tvId);

   HashSet<TvReviewCritic> findAllByCriticId(int criticId);
}
