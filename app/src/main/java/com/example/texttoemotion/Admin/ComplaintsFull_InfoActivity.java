package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivityComplaintsFullInfoBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.util.Executors;

public class ComplaintsFull_InfoActivity extends AppCompatActivity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ActivityComplaintsFullInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityComplaintsFullInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}