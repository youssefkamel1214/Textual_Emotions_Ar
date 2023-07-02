package com.example.texttoemotion.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.texttoemotion.databinding.ComplaintItemBinding;
import com.example.texttoemotion.models.Complaint;

import java.util.ArrayList;

public class ComplaintAdapter extends  RecyclerView.Adapter<ComplaintAdapter.ViewHolder>{
    ArrayList<Complaint>complaints;

    Callback<Complaint>callback;

    int height=0;
    public ComplaintAdapter(ArrayList<Complaint> complaints,Callback<Complaint>callback,int height) {
        this.complaints = complaints;
        this.callback=callback;
        this.height=height;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ComplaintItemBinding binding=ComplaintItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder( binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Complaint complaint=complaints.get(position);
        holder.binding.title.setText(complaint.getTitle());
        holder.binding.summary.setText(complaint.getSummary());
        holder.binding.datesumbit.setText( complaint.getDate());

        if(callback!=null){
            holder.binding.governorate.setText(complaint.getGovernorate());
            holder.binding.governoratelabel.setVisibility(View.VISIBLE);
            holder.binding.governorate.setVisibility(View.VISIBLE);
            holder.binding.cardViewGraph.setOnClickListener(v -> {
                callback.call(complaint);
            });
            holder.binding.cardViewGraph.getLayoutParams().height=height;
        }
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public void sumbitComplaints(ArrayList<Complaint> complaints) {
        this.complaints=complaints;
        notifyDataSetChanged();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
          ComplaintItemBinding binding;
        public ViewHolder(@NonNull ComplaintItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
