package com.maple.cse308.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="report_movie_critic_review")
public class ReportMovieCriticReview {

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name="reviewId")
    private Integer reviewId;

    @Column(name="reason")
    private String reason;


}
