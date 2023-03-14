package com.example.texttoemotion.authintication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.texttoemotion.MainActivity;
import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
        getWindow().setBackgroundDrawable(getDrawable(R.drawable.actionbar));

        binding.loginButton.setOnClickListener(view -> {
            String ssn=binding.ssnlogin.getText().toString();
            String phone=binding.phonenum.getText().toString();
            Toast.makeText(this.getApplicationContext(),ssn,Toast.LENGTH_SHORT).show();

            if(ssn.equals("30009090106017")||phone.equals("01153453880")){

                Intent intent=new Intent(this.getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }


        });
    }

}