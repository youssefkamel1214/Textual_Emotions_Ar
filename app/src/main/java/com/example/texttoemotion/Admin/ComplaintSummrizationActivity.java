package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.texttoemotion.controller.ComplaintAdapter;
import com.example.texttoemotion.controller.ComplaintListener;
import com.example.texttoemotion.databinding.ActivityComplaintSummrizationBinding;
import com.example.texttoemotion.models.Complaint;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ComplaintSummrizationActivity extends AppCompatActivity {
    ActivityComplaintSummrizationBinding binding;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ComplaintListener complaintListener;
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
        complaintListener=ComplaintListener.getInstance(db,loading -> {
            runOnUiThread(() -> {
                if(loading)binding.progressBar4.setVisibility(View.VISIBLE);
                else binding.progressBar4.setVisibility(View.GONE);
            });
        },complaints -> {
            runOnUiThread(()->{
                ComplaintAdapter complaintAdapter=new ComplaintAdapter(complaints,obj -> moveToShowFullComplaint(obj),height);
                binding.recylerview.setAdapter(complaintAdapter);
            });
        });
        if(complaintListener.getComplaints()!=null){
            ComplaintAdapter complaintAdapter=new ComplaintAdapter(complaintListener.getComplaints(),obj -> moveToShowFullComplaint(obj),height);
            binding.recylerview.setAdapter(complaintAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        complaintListener.clearlithners();
    }

    private void moveToShowFullComplaint(Complaint complaint) {
        Intent intent = new Intent(this,ComplaintsFull_InfoActivity.class);
        intent.putExtra("complaint", complaint);
        startActivity(intent);
    }
}