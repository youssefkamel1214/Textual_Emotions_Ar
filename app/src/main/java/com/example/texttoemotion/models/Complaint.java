package com.example.texttoemotion.models;

import android.icu.util.Calendar;

import java.util.Random;

public class Complaint {
    String title;
    String city;
    String governorate;
    String address;
    String organization;
    String complaintbody;
    String comments;
    String userid;
    long date;
    String summary;
    String Fileurl;

    public Complaint() {
    }

    public Complaint(String title, String city, String governorate, String address, String organization, String complaintbody, String comments, String userid, long date, String fileurl) {
        this.title = title;
        this.city = city;
        this.governorate = governorate;
        this.address = address;
        this.organization = organization;
        this.complaintbody = complaintbody;
        this.comments = comments;
        this.userid = userid;
        this.date = date;
        Fileurl = fileurl;
    }

    public Complaint(String title, String summary) {
        this.title = title;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getComplaintbody() {
        return complaintbody;
    }

    public void setComplaintbody(String complaintbody) {
        this.complaintbody = complaintbody;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getFileurl() {
        return Fileurl;
    }

    public void setFileurl(String fileurl) {
        Fileurl = fileurl;
    }
}