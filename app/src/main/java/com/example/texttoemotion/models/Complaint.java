package com.example.texttoemotion.models;

import android.icu.util.Calendar;

import java.util.Random;

public class Complaint {
    String id;
    String title;
    String governorate;
    String address;
    String organization;
    String complaintbody;
    String userid;
    String date;
    String summary;
    String Fileurl;

    public Complaint() {
    }

    public Complaint(String title, String governorate, String address, String organization, String complaintbody, String userid, String date, String fileurl) {
        this.title = title;
        this.governorate = governorate;
        this.address = address;
        this.organization = organization;
        this.complaintbody = complaintbody;
        this.userid = userid;
        this.date = date;
        Fileurl = fileurl;
    }

    public Complaint(String title, String summary) {
        this.title = title;
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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