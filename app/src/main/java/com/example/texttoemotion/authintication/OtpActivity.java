package com.example.texttoemotion.authintication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.texttoemotion.Admin.AdminActivity;
import com.example.texttoemotion.MainActivity;
import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivityOtpBinding;
import com.example.texttoemotion.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    private ActivityOtpBinding binding;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth auth;
    private User user;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String TAG="phone";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user=getIntent().getParcelableExtra("user");
        String tmp=String.format(getResources().getString(R.string.code_was_sent), user.getEmail());
        binding.SignupText.setText(tmp);
        binding.otpbutton.setOnClickListener(v -> {
            String code=binding.email.getText().toString();
            PhoneAuthCredential credential;
            if(code.length()==6) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.otpbutton.setVisibility(View.GONE);
                credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                if(credential!=null) {
                    SendOTPVerificationCode(credential);
                }
                else {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.otpbutton.setVisibility(View.VISIBLE);
                    Toast.makeText(this,"wrong code",Toast.LENGTH_LONG).show();
                }
            }
        });
        auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("ar");
    }

    private void SendOTPVerificationCode(PhoneAuthCredential credential) {
//        binding.progressBar.setVisibility(View.VISIBLE);
//        binding.otpbutton.setVisibility(View.GONE);

    }

    private void uploaddata() {
        if(getIntent().getIntExtra("login",0)==0) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(user.getId()).set(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startmainhome();
                }
            });
        }
        else{
            if(user.getType().equals("admin"))
                  startadminpage();
            else
                startmainhome();
        }
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
}