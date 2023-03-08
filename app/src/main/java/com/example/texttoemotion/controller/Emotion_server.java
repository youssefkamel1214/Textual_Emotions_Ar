package com.example.texttoemotion.controller;

import com.example.texttoemotion.models.EmotionData;
import com.example.texttoemotion.models.EmotionLabels;
import com.example.texttoemotion.models.EmotionResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Emotion_server {
    @POST("run/predict")
    Call<EmotionResponse> postEmotion(@Body RequestBody requestBody);
}
