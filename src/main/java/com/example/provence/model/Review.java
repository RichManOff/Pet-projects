package com.example.provence.model;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reviewersName;
    private String review;

    public Review(Long id, String reviewersName, String review) {
        this.id = id;
        this.reviewersName = reviewersName;
        this.review = review;
    }

    public Review() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewersName() {
        return reviewersName;
    }

    public void setReviewersName(String reviewersName) {
        this.reviewersName = reviewersName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
