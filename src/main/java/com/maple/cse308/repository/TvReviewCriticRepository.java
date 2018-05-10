package com.maple.cse308.repository;

import com.maple.cse308.entity.TvReviewCritic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TvReviewCriticRepository extends CrudRepository<TvReviewCritic, Integer> {

    List<TvReviewCritic> findAllByTvId(int tvId);

    List<TvReviewCritic> findAllByCriticId(int criticId);
}
