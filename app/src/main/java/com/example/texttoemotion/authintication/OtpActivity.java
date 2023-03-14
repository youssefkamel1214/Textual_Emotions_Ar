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
        binding.SignupText.setText(getResources().getString(R.string.code_was_sent,user.getPhone()));
        binding.otpbutton.setOnClickListener(v -> {
            String code=binding.phonenumber.getText().toString();
            PhoneAuthCredential credential;
            if(code.length()==6) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.otpbutton.setVisibility(View.GONE);
                credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                if(credential!=null) {
                    signInWithPhoneAuthCredential(credential);
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
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId+" phone:"+user.getPhone());

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+2"+user.getPhone())       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)// OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        binding.otpbutton.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser userfir = task.getResult().getUser();
                            user.setId(userfir.getUid());
                            uploaddata();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
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