package com.example.texttoemotion.models;

public class Feedback {
    String feedback;
    float review;
    String id;
    String userid;
    String date;
    String label;

    public Feedback(String feedback, float review, String id, String userid, String date, String label) {
        this.feedback = feedback;
        this.review = review;
        this.id = id;
        this.userid = userid;
        this.date = date;
        this.label = label;
    }

    public Feedback(String feedback, float review, String id, String userid, String date) {
        this.feedback = feedback;
        this.review = review;
        this.id = id;
        this.userid = userid;
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
