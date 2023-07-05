package com.example.texttoemotion.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.texttoemotion.R;
import com.example.texttoemotion.authintication.LoginActivity;
import com.example.texttoemotion.databinding.ActivityAdminBinding;
import com.google.firebase.auth.FirebaseAuth;

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            Intent intent=new Intent(this, LoginActivity.class);
            FirebaseAuth.getInstance().signOut();
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout, menu);
        return super.onCreateOptionsMenu(menu);
    }
}