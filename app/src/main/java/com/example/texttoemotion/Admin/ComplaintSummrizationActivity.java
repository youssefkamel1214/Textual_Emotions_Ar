package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.texttoemotion.controller.ComplaintAdapter;
import com.example.texttoemotion.databinding.ActivityComplaintSummrizationBinding;
import com.example.texttoemotion.models.Complaint;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ComplaintSummrizationActivity extends AppCompatActivity {
    ActivityComplaintSummrizationBinding binding;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String tag="ComplaintSummrizationActivity";
    int height=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityComplaintSummrizationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int)(displayMetrics.heightPixels*0.5);
        binding.recylerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        db.collection("complaints").get().addOnCompleteListener(task -> {
                     if(task.isSuccessful()){
                         ArrayList<Complaint> complaints=new ArrayList<Complaint>();
                         for (int i=0;i<task.getResult().getDocuments().size();i++){
                             DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(i);
                             complaints.add(documentSnapshot.toObject(Complaint.class));
                         }
                         ComplaintAdapter complaintAdapter=new ComplaintAdapter(complaints,obj -> moveToShowFullComplaint(obj),height);
                         binding.recylerview.setAdapter(complaintAdapter);
                         binding.progressBar4.setVisibility(View.GONE);
                     }
                     else {
                         Log.e(tag,task.getException().getMessage());
                     }
        });
    }

    private void moveToShowFullComplaint(Complaint complaint) {
        Log.d(tag, complaint.getTitle());
    }
}