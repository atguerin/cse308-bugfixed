package com.maple.cse308.service;


import com.maple.cse308.entity.*;
import com.maple.cse308.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieReviewCriticRepository movieReviewCriticRepository;
    @Autowired
    MovieReviewUserRepository movieReviewUserRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    MovieScreenshotRepository movieScreenshotRepository;
    @Autowired
    MovieTrailerRepository movieTrailerRepository;
    @Autowired
    MovieActorRepository movieActorRepository;

    @Override
    public Movie getMovieDetails(int movieId) {
        Movie movie = movieRepository.findMovieByMovieId(movieId);
        return movie;
    }

    @Override
    public List<MovieReviewCritic> getCriticMovieReviewsByMovie(int movieId) {
        HashSet<MovieReviewCritic> set = movieReviewCriticRepository.findAllByMovieId(movieId);
        List<MovieReviewCritic> movieReviewCritics = new LinkedList();
        movieReviewCritics.addAll(set);
            return movieReviewCritics;
        }

    @Override
    public List<MovieReviewCritic> getCriticMovieReviewsByCritic(int criticId) {
        HashSet<MovieReviewCritic> set = movieReviewCriticRepository.findAllByCriticId(criticId);
        List<MovieReviewCritic> movieReviewCritics = new LinkedList();
        movieReviewCritics.addAll(set);
        return movieReviewCritics;
    }

    @Override
    public List<MovieReviewUser> getUserMovieReviewsByMovie(int movieId) {
        HashSet<MovieReviewUser> set = movieReviewUserRepository.findAllByMovieId(movieId);
        List<MovieReviewUser> movieReviewUsers = new LinkedList();
        movieReviewUsers.addAll(set);
            return movieReviewUsers;
    }

    @Override
    public List<MovieReviewUser> getUserMovieReviewsByUser(int userId) {
        HashSet<MovieReviewUser> set = movieReviewUserRepository.findAllByUserId(userId);
        List<MovieReviewUser> movieReviewUsers = new LinkedList();
        movieReviewUsers.addAll(set);
        return movieReviewUsers;
    }

    @Override
    public List<Movie> getMoviesComingSoon() {
        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        List<Movie> movies = movieRepository.findTop10ByReleaseDateAfter(date);
        movies.sort(Comparator.comparing(Movie::getReleaseDate));
        return movies;
    }

    @Override
    public List<Movie> getMoviesOutNow() {
        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        List<Movie> movies = movieRepository.findTop10ByReleaseDateBefore(date);
        movies.sort(Comparator.comparing(Movie::getReleaseDate)
                .reversed());
        return movies;

    }

    @Override
    public List<Movie> getTopBoxOffice() {
        Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        List<Movie> movies = movieRepository.findTop10ByReleaseDateBefore(date);
        movies.sort(Comparator.comparingInt(Movie::getRevenueUnformatted)
                .reversed());
        return movies;
    }

    @Override
    public List<Movie> movieSearch(String search) {
        //String needs to be parsed, and removed for duplcates.
        String[] searchString;
        if (search.contains(" ")) {
            searchString = search.split(" ");
        } else {
            searchString = new String[1];
            searchString[0] = search;
        }
        String longest = searchString[0];
        for (String string : searchString) {
            if (string.length() > longest.length()) {
                longest = string;
            }
        }

        List<Movie> movieList = movieRepository.findAllByTitleContainingIgnoreCase(longest);
        List<Movie> resultList = new LinkedList();
        resultList.addAll(movieList);
        for (String string : searchString) {
            string = string.toLowerCase();
            for (Movie movie : movieList) {
                String movieTitle = movie.getTitle().toLowerCase();
                if (!movieTitle.contains(string)) {
                    resultList.remove(movie);
                }
            }
        }
        return resultList;
    }

    @Override
    public void addUserMovieReview(MovieReviewUser movieReviewUser) {
        movieReviewUserRepository.save(movieReviewUser);
    }

    @Override
    public void editUserMovieReview(MovieReviewUser movieReviewUser) {
        movieReviewUserRepository.save(movieReviewUser);
    }

    @Override
    public void deleteUserMovieReview(MovieReviewUser movieReviewUser) {
        movieReviewUserRepository.delete(movieReviewUser);

    }

    @Override
    public void addCriticMovieReview(MovieReviewCritic movieReviewCritic) {
        movieReviewCriticRepository.save(movieReviewCritic);

    }

    @Override
    public void editCriticMovieReview(MovieReviewCritic movieReviewCritic) {
        movieReviewCriticRepository.save(movieReviewCritic);
    }

    @Override
    public void deleteCriticMovieReview(MovieReviewCritic movieReviewCritic) {
        movieReviewCriticRepository.delete(movieReviewCritic);
    }

   @Override
    public List<Movie> findAllByTitleContainingIgnoreCase(String search) {
        return movieRepository.findAllByTitleContainingIgnoreCase(search);
    }

    @Override
    public List<Movie> getAllTimeHighestRated() {
        return movieRepository.findTop10ByRatingAvg(movieRepository.findAll());
    }

    public List<MovieScreenshot> getMovieScreenShots(int movieId){
        return movieScreenshotRepository.findAllByMovieId(movieId);
    }

    public List<MovieTrailer> getMovieTrailers(int movieId){
        return movieTrailerRepository.findAllByMovieId(movieId);
    }

    public List<MovieActor> getMovieActors(int movieId){
        return movieActorRepository.findAllByMovieId(movieId);
    }

    @Override
    public float getAverageUserRating(int movieId){
        float divisor = 0;
        float value = 0;
        HashSet<MovieReviewUser> movieSet = movieReviewUserRepository.findAllByMovieId(movieId);
        for (MovieReviewUser movieReviewUser : movieSet) {
            divisor++;
            value = value + movieReviewUser.getRating();
        }
        value = value/divisor;
        return value;
    }
}
