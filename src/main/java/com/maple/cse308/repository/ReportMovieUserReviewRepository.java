package com.maple.cse308.repository;

import com.maple.cse308.entity.ReportMovieUserReview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportMovieUserReviewRepository extends CrudRepository<ReportMovieUserReview, Integer>{
    ReportMovieUserReview save(ReportMovieUserReview reportMovieUserReview);
    List<ReportMovieUserReview> findAll();
    ReportMovieUserReview findByReviewId(Integer reviewId);
    void delete(ReportMovieUserReview reportMovieUserReview);
}
