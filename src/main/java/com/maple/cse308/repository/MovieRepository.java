package com.maple.cse308.repository;

import com.maple.cse308.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

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

    List<Movie> findAllByRatingAvgGreaterThanEqualAndRatingCountGreaterThanEqual(float ratingAvg, int ratingCount);

    @Query(value="SELECT * FROM movie WHERE rating_avg<=10  ORDER BY rating_avg DESC LIMIT 20", nativeQuery=true)
    List<Movie> findTop20OrderByRatingAvgLessThanEqual();

    List<Movie> findAllByTitleContainingIgnoreCaseBetween(String search, Date startDate, Date endDate);
}
