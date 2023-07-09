package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;

import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
        getWindow().setBackgroundDrawable(getDrawable(R.drawable.actionbar));
        binding.feedback.setOnClickListener(v -> {
            Intent intent=new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        });
        binding.summarization.setOnClickListener(v -> {
            Intent intent=new Intent(this, ComplaintSummrizationActivity.class);
            startActivity(intent);
        });

        binding.Analysis.setOnClickListener(v -> {
            Intent intent=new Intent(this, ComplaintAnalysisActivity.class);
            startActivity(intent);
        });
    }
}