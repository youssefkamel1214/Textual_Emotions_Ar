package com.example.texttoemotion.authintication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.texttoemotion.MainActivity;
import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivitySignUpBinding;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
        getWindow().setBackgroundDrawable(getDrawable(R.drawable.actionbar));
        binding.signupButton.setOnClickListener(view -> {
            String name=binding.name.getText().toString();
            String phone=binding.phonenumber.getText().toString();
            String ssn=binding.ssn.getText().toString();
            String dob=binding.dob.getText().toString();
            Toast.makeText(this.getApplicationContext(),"Signup successfully",Toast.LENGTH_SHORT).show();

            if(ssn.equals("30009090106017")||phone.equals("01153453880")){

                Intent intent=new Intent(this.getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }


        });
    }
}