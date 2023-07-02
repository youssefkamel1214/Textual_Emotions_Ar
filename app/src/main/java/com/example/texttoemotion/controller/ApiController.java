package com.example.texttoemotion.controller;

import com.example.texttoemotion.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {
    public Emotion_server server;
    private static ApiController instance;
    private ApiController() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Constants.baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        server=retrofit.create(Emotion_server.class);

    }
    public static ApiController getInstance(){
        if(instance==null){
            instance=new ApiController();
        }
        return instance;
    }
}
