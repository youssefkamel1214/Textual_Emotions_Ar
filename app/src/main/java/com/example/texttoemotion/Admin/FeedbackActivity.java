package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.texttoemotion.R;
import com.example.texttoemotion.controller.ApiController;
import com.example.texttoemotion.databinding.ActivityFeedbackBinding;
import com.example.texttoemotion.models.EmotionLabels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class FeedbackActivity extends AppCompatActivity {
    ActivityFeedbackBinding binding;

    // Create the object of TextView and PieChart class
    PieChart pieChart;
    String tag="FeedbackActivity";
    ApiController apiController;
    EmotionLabels emotionLabels;// here is where emotions count stored
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiController=ApiController.getInstance();
        apiController.server.getResponse().enqueue(new Callback<EmotionLabels>() {
            @Override
            public void onResponse(Call<EmotionLabels> call, Response<EmotionLabels> response) {
                if(response.isSuccessful()){
                    emotionLabels=response.body();
                    Log.d(tag, Integer.toString(emotionLabels.getCount()));
                    binding.progressBar5.setVisibility(View.GONE);
                    binding.graphs.setVisibility(View.VISIBLE);
                    onEmotionsLoaded();
                }
                else{
                    binding.progressBar5.setVisibility(View.GONE);
                    binding.textView.setVisibility(View.VISIBLE);
                    Toast.makeText(FeedbackActivity.this,"some thing went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EmotionLabels> call, Throwable t) {
                Log.e(tag,t.getMessage());
                Toast.makeText(FeedbackActivity.this,"some thing went wrong", Toast.LENGTH_LONG).show();
                binding.progressBar5.setVisibility(View.GONE);
                binding.textView.setVisibility(View.VISIBLE);
                binding.textView.setText(t.getMessage());
            }
        });
    }

    private void onEmotionsLoaded() {
        binding.anger.setText(Integer.toString(emotionLabels.getAnger()));
        binding.sadness.setText(Integer.toString(emotionLabels.getSadness()));
        binding.joy.setText(Integer.toString(emotionLabels.getJoy()));
        binding.surprise.setText(Integer.toString(emotionLabels.getSurprise()));
        binding.love.setText(Integer.toString(emotionLabels.getLove()));
        binding.sympathy.setText(Integer.toString(emotionLabels.getSympathy()));
        binding.fear.setText(Integer.toString(emotionLabels.getFear()));
        pieChart = binding.piechart;
// Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Anger",
                        Float.parseFloat(binding.anger.getText().toString())/emotionLabels.getCount(),
                        getResources().getColor(R.color.red)));
        pieChart.addPieSlice(
                new PieModel(
                        "sadness",
                        Float.parseFloat(binding.sadness.getText().toString())/emotionLabels.getCount(),
                        getResources().getColor(R.color.grey)));
        pieChart.addPieSlice(
                new PieModel(
                        "joy",
                        Float.parseFloat(binding.joy.getText().toString())/emotionLabels.getCount(),
                        getResources().getColor(R.color.teal_200)));
        pieChart.addPieSlice(
                new PieModel(
                        "surprise",
                        Float.parseFloat(binding.surprise.getText().toString())/emotionLabels.getCount(),
                        getResources().getColor(R.color.yellow)));
        pieChart.addPieSlice(
                new PieModel(
                        "love",
                        Float.parseFloat(binding.love.getText().toString())/emotionLabels.getCount(),
                        getResources().getColor(R.color.colorAccent)));
        pieChart.addPieSlice(
                new PieModel(
                        "sympathy",
                        Float.parseFloat(binding.sympathy.getText().toString())/emotionLabels.getCount(),
                        getResources().getColor(R.color.bluefateh)));
        pieChart.addPieSlice(
                new PieModel(
                        "fear",
                        Float.parseFloat(binding.fear.getText().toString())/emotionLabels.getCount(),
                        getResources().getColor(R.color.colorPrimaryDark)));
        pieChart.startAnimation();
    }
}