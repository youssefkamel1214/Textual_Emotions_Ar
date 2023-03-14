package com.example.texttoemotion.authintication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.texttoemotion.Admin.AdminActivity;
import com.example.texttoemotion.MainActivity;
import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivityLoginBinding;
import com.example.texttoemotion.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
        getWindow().setBackgroundDrawable(getDrawable(R.drawable.actionbar));
        binding.signupText.setOnClickListener(v -> {
            Intent intent=new Intent(this,SignUpActivity.class);
            startActivity(intent);
        });
        binding.loginButton.setOnClickListener(view -> {
            String ssn=binding.ssnlogin.getText().toString().trim();
            String phone=binding.phonenum.getText().toString().trim();
            binding.loginButton.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            if(ssn.isEmpty()|| !phone.contains("01")){
                Toast.makeText(this,"please fill all feilds",Toast.LENGTH_LONG).show();
                binding.loginButton.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                return;
            }
            confirmdata(ssn,phone);
        });
    }

    private void confirmdata(String ssn, String phone) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("ssn",ssn).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(queryDocumentSnapshots.isEmpty()){
                Toast.makeText(this,"there no ssn like that",Toast.LENGTH_LONG).show();
                binding.loginButton.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                return;
            }
            else {
                User user= queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){

                    Intent intent;
                    if(user.getType().equals("admin"))
                         intent = new Intent(this, AdminActivity.class);
                    else
                          intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(this, OtpActivity.class);
                    intent.putExtra("user",user);
                    intent.putExtra("login",1);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(e -> {
            binding.loginButton.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        });
    }

}