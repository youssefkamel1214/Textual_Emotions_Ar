package com.example.texttoemotion.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.texttoemotion.databinding.ComplaintItemBinding;
import com.example.texttoemotion.databinding.DatetimerecleBinding;

import java.util.ArrayList;

public class DatedaysController extends  RecyclerView.Adapter<DatedaysController.ViewHolder> {
    ArrayList<String>dates;
    Callback<String>dateclick;

    public DatedaysController(ArrayList<String> dates, Callback<String> dateclick) {
        this.dates = dates;
        this.dateclick = dateclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DatetimerecleBinding binding=DatetimerecleBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          String date=dates.get(position);
          String[] arr =date.split("-");
          holder.binding.cardview.setOnClickListener(v -> {dateclick.call(date);});
          holder.binding.dayWeek.setText( arr[0]);
          holder.binding.dayMounth.setText( arr[1]);
          holder.binding.mounthName.setText( arr[2]);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class  ViewHolder extends RecyclerView.ViewHolder{
        DatetimerecleBinding binding;
        public ViewHolder(@NonNull DatetimerecleBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
