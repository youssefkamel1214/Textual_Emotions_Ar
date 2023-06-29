package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.texttoemotion.databinding.ActivityFeedbackBinding;

public class FeedbackActivity extends AppCompatActivity {
    ActivityFeedbackBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}