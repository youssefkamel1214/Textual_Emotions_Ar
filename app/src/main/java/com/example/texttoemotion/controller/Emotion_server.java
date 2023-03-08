package com.example.texttoemotion.controller;

import com.example.texttoemotion.models.EmotionData;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Emotion_server {
    @POST("run/predict")
    Call<EmotionData> postEmotion(@Body RequestBody requestBody);
}
