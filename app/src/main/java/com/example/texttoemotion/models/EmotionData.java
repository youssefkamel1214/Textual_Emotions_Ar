package com.example.texttoemotion.models;

import java.util.List;

public class EmotionData {
    private List<String> data;

    public EmotionData(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}