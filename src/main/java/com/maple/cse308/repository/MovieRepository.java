package com.maple.cse308.repository;

import com.maple.cse308.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    Movie findMovieByMovieId(int movieId);

    List<Movie> findTop10ByReleaseDateAfter(Date date);

    List<Movie> findTop10ByReleaseDateBefore(Date date);

    List<Movie> findTop10ByRatingAvg(List<Movie> movieList);

    Movie save(Movie m);

    boolean existsByTitleAndReleaseDate(String title, Date releaseDate);

    Movie findByTitleAndReleaseDate(String title, Date releaseDate);

    List<Movie> findAllByTitleContainingIgnoreCase(String search);

    List<Movie> findAll();
}
