package com.example.texttoemotion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.texttoemotion.controller.Emotion_server;
import com.example.texttoemotion.databinding.ActivityMainBinding;
import com.example.texttoemotion.models.EmotionData;
import com.example.texttoemotion.models.EmotionLabels;
import com.example.texttoemotion.models.EmotionResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Emotion_server service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://youssef1214-text-to-emotion.hf.space/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Emotion_server.class);
        binding.button.setOnClickListener(v -> getinputanddofilter());
    }

    private void getinputanddofilter() {
        String s= binding.input.getText().toString().trim();
        if(s.isEmpty()){
            Toast.makeText(this,"لو سمحت دخل داتا اديني مشتمتش يا عم محمود", Toast.LENGTH_LONG).show();
            return;
        }
        binding.button.setVisibility(View.GONE);
        ArrayList<String> strings=new ArrayList<String>();
        Log.d("aa",s);
        strings.add(s);
        Getemotions(strings);
        binding.result.setText("بنحمل نعمل في api");

    }

    private void Getemotions(ArrayList<String>data){
        //Create an instance of the request body with some data
        EmotionData emotionData = new EmotionData(data);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                new Gson().toJson(emotionData));

        Call<EmotionResponse> call = service.postEmotion(requestBody);
        call.enqueue(new Callback<EmotionResponse>() {
            @Override
            public void onResponse(Call<EmotionResponse> call, Response<EmotionResponse> response) {
                // Handle success response here
                binding.button.setVisibility(View.VISIBLE);
                EmotionResponse result=response.body();
                whendatacome(result.getData().get(0));
            }

            @Override
            public void onFailure(Call<EmotionResponse> call, Throwable t) {
                // Handle failure response here
                binding.result.setText("here we go again error:"+t.getMessage());
                binding.button.setVisibility(View.VISIBLE);
                Log.d("Retrofit", "Failure: " + t.getMessage());

            }
        });
    }

    private void whendatacome(EmotionLabels result) {
        String s="";
        for (EmotionLabels.Emotions sa:result.getEmotions()) {
            s=s+sa.getLabel()+" "+sa.getConfidence().toString()+"\n";
        }
        binding.result.setText(s);
    }
}