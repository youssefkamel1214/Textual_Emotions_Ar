package com.example.texttoemotion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.texttoemotion.Home.HomeFragment;
import com.example.texttoemotion.Profile.ProfileFragment;
import com.example.texttoemotion.Settings.SettingsFragment;
import com.example.texttoemotion.authintication.LoginActivity;
import com.example.texttoemotion.controller.Emotion_server;
import com.example.texttoemotion.databinding.ActivityMainBinding;
import com.example.texttoemotion.databinding.CustomBarDialogBinding;
import com.example.texttoemotion.uplaodcomplaint.AddComplaintFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;
    Emotion_server service;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
        getWindow().setBackgroundDrawable(getDrawable(R.drawable.actionbar));
        binding.bottomnav.setSelectedItemId(R.id.home);
            binding.bottomnav.setOnItemSelectedListener(item -> {
                        Fragment F = null;
                        switch(item.getItemId()){
                            case R.id.home:
                                F = new HomeFragment();
                                break;
                            case R.id.logout:
                                showLogoutDialog();
                                break;
                            case R.id.profile:
                                F = new ProfileFragment();
                                break;
                            case R.id.addcomplaint:
                                F = new AddComplaintFragment();
                                break;

                        }
                 if(F!=null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,F).commit();
                return true;
        });
    }

    private void showLogoutDialog() {
        CustomBarDialogBinding barDialogBinding= CustomBarDialogBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(barDialogBinding.getRoot());
        barDialogBinding.yes.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            dialog.dismiss();
        });
        barDialogBinding.no.setOnClickListener(v -> {
            dialog.dismiss();
            binding.bottomnav.setSelectedItemId(R.id.home);
            Fragment F=new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,F).commit();
        });
        dialog.show();
    }

    public void change_to_home(){
       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag,new HomeFragment()).commit();
       binding.bottomnav.setSelectedItemId(R.id.home);
   }


}