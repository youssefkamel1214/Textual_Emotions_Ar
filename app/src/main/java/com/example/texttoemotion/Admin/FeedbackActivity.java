package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivityFeedbackBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class FeedbackActivity extends AppCompatActivity {
    ActivityFeedbackBinding binding;

    // Create the object of TextView and PieChart class
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.anger.setText(Float.toString(40));
        binding.sadness.setText(Float.toString(22));
        binding.joy.setText(Float.toString(7));
        binding.surprise.setText(Float.toString(1));
        binding.love.setText(Float.toString(20));
        binding.sympathy.setText(Float.toString(5));
        binding.fear.setText(Float.toString(5));
        pieChart = binding.piechart;
// Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Anger",
                        Float.parseFloat(binding.anger.getText().toString()),
                        getResources().getColor(R.color.red)));
        pieChart.addPieSlice(
                new PieModel(
                        "sadness",
                        Float.parseFloat(binding.sadness.getText().toString()),
                        getResources().getColor(R.color.grey)));
        pieChart.addPieSlice(
                new PieModel(
                        "joy",
                        Float.parseFloat(binding.joy.getText().toString()),
                        getResources().getColor(R.color.teal_200)));
        pieChart.addPieSlice(
                new PieModel(
                        "surprise",
                        Float.parseFloat(binding.surprise.getText().toString()),
                        getResources().getColor(R.color.yellow)));
        pieChart.addPieSlice(
                new PieModel(
                        "love",
                        Float.parseFloat(binding.love.getText().toString()),
                        getResources().getColor(R.color.colorAccent)));
        pieChart.addPieSlice(
                new PieModel(
                        "sympathy",
                        Float.parseFloat(binding.sympathy.getText().toString()),
                        getResources().getColor(R.color.bluefateh)));
        pieChart.addPieSlice(
                new PieModel(
                        "fear",
                        Float.parseFloat(binding.fear.getText().toString()),
                        getResources().getColor(R.color.colorPrimaryDark)));
        pieChart.startAnimation();
    }


}