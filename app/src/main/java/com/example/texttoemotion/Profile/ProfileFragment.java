package com.example.texttoemotion.Profile;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.texttoemotion.Constants;
import com.example.texttoemotion.MainActivity;
import com.example.texttoemotion.controller.UserAccountdata;
import com.example.texttoemotion.databinding.FragmentProfileBinding;
import com.example.texttoemotion.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class ProfileFragment extends Fragment {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private FragmentProfileBinding binding;
    private User user;
    private String tag="ProfileFragment";
    private SimpleDateFormat DMY=new SimpleDateFormat("MMM-dd-YYYY");
    Boolean emailChange=false;
    Boolean passwordChange=false;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        new Thread(() -> {

            user= UserAccountdata.getInstance(FirebaseAuth.getInstance(),db,obj -> {
                Toast.makeText(requireContext(),obj.getMessage(),Toast.LENGTH_LONG).show();
                Log.e(tag,obj.getMessage());
            }).getCurrentUser();
            getActivity().runOnUiThread(() -> {
                //Created new thread to handel users
                binding.Name.setText(user.getName());
                binding.ssn.setText(user.getSsn());
                binding.Emailinfo.setText(user.getEmail());
                binding.PwInfo.setText(user.getPassword());
                Calendar cal=Calendar.getInstance();
                cal.setTimeInMillis(user.getDate_birth());
                binding.DateOfBirthInfo.setText(DMY.format(cal.getTime()));
            });

        }).start();

        binding.editEmail.setOnClickListener(v -> {
            binding.Emailinfo.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(binding.Emailinfo, InputMethodManager.SHOW_IMPLICIT);
        });
        binding.editPassword.setOnClickListener(v -> {
            binding.PwInfo.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(binding.PwInfo, InputMethodManager.SHOW_IMPLICIT);
        });
        binding.appCompatButton2.setOnClickListener(v -> {
            if(binding.Emailinfo.getText().toString().isEmpty()|| !Constants.checkPassword(binding.PwInfo.getText().toString())){
                Toast.makeText(requireContext(),"invalid Email or password format",Toast.LENGTH_LONG).show();
                return;
            }
            submitChanges();
        });
        iniliazecheckonchanges();
        return binding.getRoot();

    }

    private void submitChanges() {
        binding.appCompatButton2.setVisibility(View.GONE);
        binding.progressBar2.setVisibility(View.VISIBLE);
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), user.getPassword());
        user.setEmail(binding.Emailinfo.getText().toString().trim());
        user.setPassword(binding.PwInfo.getText().toString().trim());
        FirebaseUser Fuser= firebaseAuth.getCurrentUser();
        new Thread(() -> {
           Task<Void>treauthenticate= Fuser.reauthenticate(credential);
            try {
                Tasks.await(  Fuser.reauthenticate(credential));
                if(!treauthenticate.isSuccessful()){
                        onFaulire(treauthenticate.getException());
                }
                if(emailChange){
                    treauthenticate=   Fuser.updateEmail(user.getEmail());
                    Tasks.await(treauthenticate);
                    if(!treauthenticate.isSuccessful()){
                            onFaulire(treauthenticate.getException());
                    }
                }
                if(passwordChange) {
                    treauthenticate = Fuser.updatePassword(user.getPassword());
                    Tasks.await(treauthenticate);
                    if (!treauthenticate.isSuccessful()) {
                        onFaulire(treauthenticate.getException());
                    }
                }
                treauthenticate= db.collection("users").document(firebaseAuth.getUid()).set(user);
                Tasks.await(treauthenticate);
                if(!treauthenticate.isSuccessful()){
                    onFaulire(treauthenticate.getException());
                }
                getActivity().runOnUiThread(() -> {
                    MainActivity mainActivity = (MainActivity) requireActivity();
                    mainActivity.change_to_home();
                });
            } catch (ExecutionException e) {
                Log.e(tag,e.getMessage());
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();





    }

    private void onFaulire(Exception exception) {
        getActivity().runOnUiThread(() -> {
            Toast.makeText(requireContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
            binding.progressBar2.setVisibility(View.GONE);
            binding.appCompatButton2.setVisibility(View.VISIBLE);
        });

    }

    private void iniliazecheckonchanges() {
        binding.Emailinfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(user.getEmail().equals(s.toString())){
                    emailChange=false;
                }else {
                    emailChange=true;
                }
                chechkshowbutton();
            }
        });
        binding.PwInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
             if(user.getPassword().equals(s.toString())){
                 passwordChange=false;
             }else {
                 passwordChange=true;
             }
             chechkshowbutton();
            }
        });
    }

    private void chechkshowbutton() {
        if(emailChange||passwordChange){
            binding.appCompatButton2.setVisibility(View.VISIBLE);
        }else{
            binding.appCompatButton2.setVisibility(View.GONE);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}