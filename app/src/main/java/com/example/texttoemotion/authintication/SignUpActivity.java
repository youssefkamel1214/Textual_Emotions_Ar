package com.example.texttoemotion.authintication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.texttoemotion.Admin.AdminActivity;
import com.example.texttoemotion.Constants;
import com.example.texttoemotion.MainActivity;
import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivitySignUpBinding;
import com.example.texttoemotion.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private User user;
    Calendar date;
    private SimpleDateFormat DMY=new SimpleDateFormat("dd-MM-YYYY");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
        getWindow().setBackgroundDrawable(getDrawable(R.drawable.actionbar));
        date= Constants.return_hour_to_zero(Calendar.getInstance());
        binding.dob.setOnClickListener(v -> {
            pickdate();
        });
        binding.signupButton.setOnClickListener(view -> {
            binding.signupButton.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            String name=binding.name.getText().toString().trim();
            String email=binding.email.getText().toString().trim();
            String password=binding.password.getText().toString().trim();
            String ssn=binding.ssn.getText().toString().trim();
            String dob=binding.dob.getText().toString().trim();
            Pattern passwordPattern=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$");
            if(ssn.isEmpty()||email.isEmpty()||name.isEmpty()||dob.isEmpty()|| !email.contains("@")||!Constants.checkPassword(password)){
                Toast.makeText(this,"please fill all feilds",Toast.LENGTH_LONG).show();
                binding.signupButton.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                return;
            }
            user=new User(name,"",email,password,ssn,date.getTimeInMillis());
//            Intent intent=new Intent(this, OtpActivity.class);
//            intent.putExtra("user",user);
//            startActivity(intent);
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    user.setId(FirebaseAuth.getInstance().getUid());
                    uploaddata();
                }
                else {
                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
    private void uploaddata() {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(user.getId()).set(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startmainhome();
                }
            });
    }

    private void startadminpage() {
        Intent intent = new Intent(this, AdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("user",user);
        startActivity(intent);
    }

    private void startmainhome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("user",user);
        startActivity(intent);
    }
    private void pickdate() {
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth, (datePicker, year, month, day) -> {
            date.set(Calendar.YEAR,year);
            date.set(Calendar.MONTH,month);
            date.set(Calendar.DAY_OF_MONTH,day);
            binding.dob.setText(DMY.format(date.getTime()));
        },date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setTitle("Select date");
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}