package com.example.texttoemotion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.texttoemotion.Admin.AdminActivity;
import com.example.texttoemotion.authintication.LoginActivity;
import com.example.texttoemotion.controller.UserAccountdata;
import com.example.texttoemotion.databinding.ActivitySplashBinding;
import com.example.texttoemotion.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {
    ActivitySplashBinding binding;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseFirestore fireStore=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new Thread(() -> {
                        if(firebaseAuth.getCurrentUser()!=null){
                            User user= UserAccountdata.getInstance(firebaseAuth,fireStore, obj -> {
                                runOnUiThread(() -> {
                                    Toast.makeText(SplashActivity.this,obj.getMessage(),Toast.LENGTH_LONG ).show();

                                });
                            }).getCurrentUser();
                            runOnUiThread(() -> {
                                if(user.getType().equals("user")){
                                    Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Intent intent=new Intent(SplashActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                        else {
                              runOnUiThread(() -> {
                                  movetosignup();
                              });
                        }
        }).start();
    }

    private void movetosignup() {
        Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}