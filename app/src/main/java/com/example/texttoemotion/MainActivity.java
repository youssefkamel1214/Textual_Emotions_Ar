package com.example.texttoemotion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.texttoemotion.Home.HomeFragment;
import com.example.texttoemotion.Profile.ProfileFragment;
import com.example.texttoemotion.Settings.SettingsFragment;
import com.example.texttoemotion.controller.Emotion_server;
import com.example.texttoemotion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    Emotion_server service;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
        getWindow().setBackgroundDrawable(getDrawable(R.drawable.actionbar));
        binding.bottomnav.setSelectedItemId(R.id.home);
            binding.bottomnav.setOnItemSelectedListener(item -> {
                        Fragment F = null;
                        switch(item.getItemId()){
                            case R.id.home:
                                F = new HomeFragment();
                                break;
                            case R.id.settings:
                                F = new SettingsFragment();
                                break;
                            case R.id.profile:
                                F = new ProfileFragment();
                                break;
                            case R.id.addcomplaint:
                                F = new AddComplaintFragment();
                                break;

                        }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,F).commit();
                return true;
        });
    }

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://youssef1214-text-to-emotion.hf.space/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        service = retrofit.create(Emotion_server.class);


}