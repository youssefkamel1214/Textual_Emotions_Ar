package com.example.texttoemotion.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.texttoemotion.databinding.ComplaintItemBinding;
import com.example.texttoemotion.models.Complaint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ComplaintAdapter extends  RecyclerView.Adapter<ComplaintAdapter.ViewHolder>{
    ArrayList<Complaint>complaints;
    SimpleDateFormat DMY=new SimpleDateFormat("dd-MMM-YYYY");

    public ComplaintAdapter(ArrayList<Complaint> complaints) {
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ComplaintItemBinding binding=ComplaintItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder( binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Calendar calendar=Calendar.getInstance();
        Complaint complaint=complaints.get(position);
        calendar.setTimeInMillis(complaint.getDate());
        holder.binding.title.setText(complaint.getTitle());
        holder.binding.summary.setText(complaint.getSummary());
        holder.binding.datesumbit.setText( DMY.format(calendar.getTime()));
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
          ComplaintItemBinding binding;
        public ViewHolder(@NonNull ComplaintItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
