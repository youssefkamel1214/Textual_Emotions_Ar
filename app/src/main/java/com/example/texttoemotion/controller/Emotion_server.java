package com.example.texttoemotion.controller;



import com.example.texttoemotion.models.EmotionLabels;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Emotion_server {
    @GET("emotionCount")
    Call<EmotionLabels> getResponse();
}
