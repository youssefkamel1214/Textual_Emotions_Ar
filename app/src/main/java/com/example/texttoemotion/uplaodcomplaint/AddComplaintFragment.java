package com.example.texttoemotion.uplaodcomplaint;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.texttoemotion.Constants;
import com.example.texttoemotion.MainActivity;
import com.example.texttoemotion.R;
import com.example.texttoemotion.controller.Callback;
import com.example.texttoemotion.controller.UploadComplaint;
import com.example.texttoemotion.databinding.FragmentAddComplaintBinding;
import com.example.texttoemotion.models.Complaint;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddComplaintFragment extends Fragment {
    public static final int PICKFILE_RESULT_CODE = 1;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private SimpleDateFormat DMY=new SimpleDateFormat("dd-MM-YYYY");
    Calendar date;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private String filePath;


    // TODO: Rename and change types of parameters


    public AddComplaintFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentAddComplaintBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddComplaintBinding.inflate(getLayoutInflater(),container,false);
        // Inflate the layout for this fragment

        date= Constants.return_hour_to_zero(Calendar.getInstance());
        binding.attachment.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // You have the permission
                    uploadattachtment();
            } else {
                // You don't have the permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
            }

        });
        binding.submit.setOnClickListener(v -> {
            checkvaluesandsumbit();
        });
        binding.date.setOnClickListener(v -> {
            pickdate();
        });
        return binding.getRoot();
    }

    private void checkvaluesandsumbit() {
        String title=binding.title.getText().toString().trim();
        String address=binding.address.getText().toString().trim();
        String governorate=binding.governorate.getText().toString().trim();
        String organization=binding.Organization.getText().toString().trim();
        String complaintBody=binding.complaintBody.getText().toString().trim();
        String datetmp=binding.date.getText().toString().trim();
        if(title.isEmpty()||address.isEmpty()||governorate.isEmpty()||organization.isEmpty()||complaintBody.isEmpty()||datetmp.isEmpty()){
            Toast.makeText(requireContext(),"please fill all required feilds",Toast.LENGTH_LONG).show();
            return;
        }
        Complaint complaint=new Complaint(title,governorate,address,organization,complaintBody,
                FirebaseAuth.getInstance().getCurrentUser().getUid(),date.getTimeInMillis(),filePath);
        UploadComplaint uploadComplaint=new UploadComplaint(requireContext(), obj -> {
            if(obj==null) {
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.change_to_home();
            }
            else{
                Toast.makeText(requireContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
            }
        },filePath!=null &&!filePath.isEmpty() );
        uploadComplaint.execute(complaint);
    }

    private void uploadattachtment() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    Uri fileUri = data.getData();
                    filePath=fileUri.toString();

                    Log.d("from",fileUri.getPath());
                    binding.attachmentpath.setText(filePath);
                    binding.attachmentpath.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE) {
            // Check if the permission was granted or denied
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                uploadattachtment();
            } else {
                // Permission was denied
            }
        }

    }
    private void pickdate() {
        DatePickerDialog datePickerDialog=new DatePickerDialog(requireContext(), android.R.style.Theme_Holo_Dialog_MinWidth, (datePicker, year, month, day) -> {
            date.set(Calendar.YEAR,year);
            date.set(Calendar.MONTH,month);
            date.set(Calendar.DAY_OF_MONTH,day);
            binding.date.setText(DMY.format(date.getTime()));
        },date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setTitle("Select date");
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }
}