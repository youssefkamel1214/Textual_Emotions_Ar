package com.example.texttoemotion.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EmotionLabels {

    public class Emotions{
       private String label;
       private Float confidence;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Float getConfidence() {
            return confidence;
        }

        public void setConfidence(Float confidence) {
            this.confidence = confidence;
        }
    }

    public EmotionLabels(String mainlabel, List<Emotions> emotions) {
        this.mainlabel = mainlabel;
        this.emotions = emotions;
    }

    @SerializedName("label")
    private String mainlabel;
    @SerializedName("confidences")
    private List<Emotions> emotions;

    public String getMainlabel() {
        return mainlabel;
    }

    public void setMainlabel(String mainlabel) {
        this.mainlabel = mainlabel;
    }

    public List<Emotions> getEmotions() {
        return emotions;
    }

    public void setEmotions(ArrayList<Emotions> emotions) {
        this.emotions = emotions;
    }
}
