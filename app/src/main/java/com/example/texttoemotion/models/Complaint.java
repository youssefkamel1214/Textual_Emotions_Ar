package com.example.texttoemotion.models;

import android.icu.util.Calendar;

import java.util.Random;

public class Complaint {
    String title;
    String username;
    String phonenumber;
    String ssn;
    long date;
    String summary;

    public Complaint(String title, String username, String phonenumber, String ssn, long date, String summary) {
        this.title = title;
        this.username = username;
        this.phonenumber = phonenumber;
        this.ssn = ssn;
        this.date = date;
        this.summary = summary;
    }

    public Complaint(String title, String summary) {
        this.title = title;
        this.summary = summary;
        Random random = new Random();
        this.date=System.currentTimeMillis() - random.nextInt(1000) * 24 * 60 * 60 * 1000;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}