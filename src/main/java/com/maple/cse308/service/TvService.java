package com.maple.cse308.service;

import com.maple.cse308.entity.TvReviewCritic;
import com.maple.cse308.entity.TvReviewUser;
import com.maple.cse308.entity.TvShow;

import java.util.List;

public interface TvService {

    TvShow getTvShowDetails(int tvId);

    void addCriticTvReview(TvReviewCritic TvReviewCritic);

    void editCriticTvReview(TvReviewCritic TvReviewCritic);

    void deleteCriticTvReview(TvReviewCritic TvReviewCritic);

    void addUserTvReview(TvReviewUser TvReviewUser);

    void editUserTvReview(TvReviewUser TvReviewUser);

    void deleteUserTvReview(TvReviewUser TvReviewUser);

    List<TvReviewCritic> getCriticTvReviewsByTvShow(int tvId) throws Exception;

    List<TvReviewCritic> getCriticTvReviewsByCritic(int criticId) throws Exception;

    List<TvReviewUser> getUserTvReviewsByTvShow(int tvId) throws Exception;

    List<TvReviewUser> getUserTvReviewsByUser(int userId) throws Exception;

    List<TvShow> findAllByTitleContainingIgnoreCase(String search);

    List<TvShow> tvSearch(String search);
}
