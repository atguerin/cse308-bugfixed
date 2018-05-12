package com.maple.cse308.service;

import com.maple.cse308.entity.Movie;
import com.maple.cse308.entity.MovieReviewCritic;
import com.maple.cse308.entity.MovieReviewUser;

import java.util.List;

public interface MovieService {

    Movie getMovieDetails(int movieId);

    List<MovieReviewCritic> getCriticMovieReviewsByMovie(int movieId) throws Exception;

    List<MovieReviewCritic> getCriticMovieReviewsByCritic(int criticId) throws Exception;

    List<MovieReviewUser> getUserMovieReviewsByMovie(int movieId) throws Exception;

    List<MovieReviewUser> getUserMovieReviewsByUser(int userId) throws Exception;

    List<Movie> getMoviesComingSoon();

    List<Movie> getMoviesOutNow();

    List<Movie> getTopBoxOffice();

    void addCriticMovieReview(MovieReviewCritic movieReviewCritic);

    void editCriticMovieReview(MovieReviewCritic movieReviewCritic);

    void deleteCriticMovieReview(MovieReviewCritic movieReviewCritic);

    void addUserMovieReview(MovieReviewUser movieReviewUser);

    void editUserMovieReview(MovieReviewUser movieReviewUser);

    void deleteUserMovieReview(MovieReviewUser movieReviewUser);

    List<Movie> movieSearch(String search);

    List<Movie> findAllByTitleContainingIgnoreCase(String search);

    List<Movie> getAllTimeHighestRated();

}

