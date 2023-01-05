package com.app.b4s.model;

public class HomeWorkSubject {
    String not_started, completed, on_review;
           Subject subject;

    public HomeWorkSubject(String sub_name, String started, String review, Subject subject) {
        this.not_started = sub_name;
        this.completed = started;
        this.on_review = review;
        this.subject=subject;

    }

    public String getNot_started() {
        return not_started;
    }

    public void setNot_started(String not_started) {
        this.not_started = not_started;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getOn_review() {
        return on_review;
    }

    public void setOn_review(String on_review) {
        this.on_review = on_review;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
