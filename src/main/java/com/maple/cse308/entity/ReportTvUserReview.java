package com.maple.cse308.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="report_tv_user_review")
public class ReportTvUserReview {

    @Id
    @Column(name="reviewId")
    private Integer reviewId;

    @Column(name="reason")
    private String reason;

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
}
