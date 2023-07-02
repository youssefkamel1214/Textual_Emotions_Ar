package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Toast;

import com.example.texttoemotion.R;
import com.example.texttoemotion.authintication.LoginActivity;
import com.example.texttoemotion.databinding.ActivityComplaintsFullInfoBinding;
import com.example.texttoemotion.models.Complaint;
import com.example.texttoemotion.models.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.util.Executors;

public class ComplaintsFull_InfoActivity extends AppCompatActivity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    ActivityComplaintsFullInfoBinding binding;
    User user ;
    Complaint complaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityComplaintsFullInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        complaint = (Complaint)getIntent().getSerializableExtra("complaint");
        db.collection("users").document(complaint.getUserid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                user=task.getResult().toObject(User.class);
                onfinishloading();
                binding.progressBar6.setVisibility(View.GONE);
                binding.scrolladmin.setVisibility(View.VISIBLE);
            }

        });
        binding.review.setOnClickListener(view -> {

            if (!complaint.isStatus()){
                complaint.setStatus(true);
                db.collection("complaints").document(complaint.getId()).update("status",true).addOnCompleteListener(task -> {

                    if(task.isSuccessful()){

                        Toast.makeText(ComplaintsFull_InfoActivity.this,"Finished",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        Toast.makeText(ComplaintsFull_InfoActivity.this,"Zeka msh shayef sho8lo sah",Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(ComplaintsFull_InfoActivity.this,"Already Reviewed",Toast.LENGTH_LONG).show();

            }

        });

    }

    private void onfinishloading() {
        // UI binding
        binding.txtU.setText(user.getName());
        binding.txtTitle.setText(complaint.getTitle());
        binding.txtDate.setText(complaint.getDate());
        binding.txtAddress.setText(complaint.getAddress());
        binding.txtOrganization.setText(complaint.getOrganization());
        binding.txtGovernrate.setText(complaint.getGovernorate());
        binding.txtComplainBody.setText(complaint.getComplaintbody());
        binding.txtSummary.setText(complaint.getSummary());
        binding.txtFileurl.setText(complaint.getFileurl());
        Linkify.addLinks(binding.txtFileurl, Linkify.WEB_URLS);
    }
}