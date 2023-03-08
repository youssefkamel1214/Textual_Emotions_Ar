package com.example.texttoemotion.models;

import java.util.List;

public class EmotionResponse {
    List<EmotionLabels> data;

    public List<EmotionLabels> getData() {
        return data;
    }

    public void setData(List<EmotionLabels> data) {
        this.data = data;
    }
}
