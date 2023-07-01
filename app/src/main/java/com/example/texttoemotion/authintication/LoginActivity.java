package com.example.texttoemotion.authintication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.texttoemotion.Admin.AdminActivity;
import com.example.texttoemotion.Constants;
import com.example.texttoemotion.MainActivity;
import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivityLoginBinding;
import com.example.texttoemotion.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        check_login();
        getWindow().setStatusBarColor(ContextCompat.getColor(this,android.R.color.transparent));
        getWindow().setBackgroundDrawable(getDrawable(R.drawable.actionbar));
        binding.signupText.setOnClickListener(v -> {
            Intent intent=new Intent(this,SignUpActivity.class);
            startActivity(intent);
        });
        binding.loginButton.setOnClickListener(view -> {
            String email=binding.email.getText().toString().trim();
            String password=binding.password.getText().toString().trim();
            binding.loginButton.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            if(email.isEmpty()|| !Constants.checkPassword(password)){
                Toast.makeText(this,"please fill all feilds",Toast.LENGTH_LONG).show();
                binding.loginButton.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                return;
            }
            confirmdata(email,password);
        });
    }

    private void check_login() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void confirmdata(String email, String password) {
      auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                new Thread(() -> {
                    Task<DocumentSnapshot> usercollection=  db.collection("users").document(auth.getUid()).get();
                    try {
                        Tasks.await(usercollection);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                     User user=usercollection.getResult().toObject(User.class);
                    runOnUiThread(() -> {
                            binding.loginButton.setVisibility(View.VISIBLE);
                            binding.progressBar.setVisibility(View.GONE);
                            if(user.getType().equals("user")){
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intent=new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                                finish();
                            }
                    });

                }).start();

            }else {
                Toast.makeText(this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                binding.loginButton.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);

            }
        });
//        db.collection("users").whereEqualTo("ssn",ssn).get().addOnSuccessListener(queryDocumentSnapshots -> {
//            if(queryDocumentSnapshots.isEmpty()){
//                Toast.makeText(this,"there no ssn like that",Toast.LENGTH_LONG).show();
//                binding.loginButton.setVisibility(View.VISIBLE);
//                binding.progressBar.setVisibility(View.GONE);
//                return;
//            }
//            else {
//                User user= queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
//
//                    Intent intent=new Intent(this, OtpActivity.class);
//                    intent.putExtra("user",user);
//                    intent.putExtra("login",1);
//                    startActivity(intent);
//
//            }
//        }).addOnFailureListener(e -> {
//            binding.loginButton.setVisibility(View.VISIBLE);
//            binding.progressBar.setVisibility(View.GONE);
//            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
//        });
    }

}