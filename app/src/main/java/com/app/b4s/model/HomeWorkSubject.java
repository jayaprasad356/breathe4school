package com.app.b4s.model;

public class HomeWorkSubject {
    String sub_name,started,review,status;

    public HomeWorkSubject(String sub_name, String started, String review, String status) {
        this.sub_name = sub_name;
        this.started = started;
        this.review = review;
        this.status = status;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
