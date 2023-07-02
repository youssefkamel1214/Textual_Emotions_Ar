package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.texttoemotion.R;
import com.example.texttoemotion.controller.DatedaysController;
import com.example.texttoemotion.databinding.ActivityComplaintAnalysisBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComplaintAnalysisActivity extends AppCompatActivity {
    ActivityComplaintAnalysisBinding binding;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    String tag="ComplaintAnalysisActivity";
    Map<String, ArrayList<Map.Entry<String,Object>>>datacommonwords=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityComplaintAnalysisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db.collection("per_day_common_words").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                ArrayList<String>dates=new ArrayList<>();
                for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()) {
                    dates.add(documentSnapshot.getId());
                    Log.d(tag, documentSnapshot.getId());
                    ArrayList<Map.Entry<String,Object>> list = new ArrayList<>(documentSnapshot.getData().entrySet());
                    list.sort((o1, o2) -> {
                        long value1=(long)o1.getValue(),value2=(long)o2.getValue();
                        if(value1>value2)
                            return -1;
                        else if (value1==value2)
                            return 0;
                        else
                            return 1;
                    });
                    datacommonwords.put(documentSnapshot.getId(),list);
                    binding.progressBar7.setVisibility(View.GONE);
                    binding.relativeLayout.setVisibility(View.VISIBLE);
                    binding.idBarChart.setVisibility(View.VISIBLE);
                    binding.dateitem.setVisibility(View.VISIBLE);
                    DatedaysController datedaysController=new DatedaysController(dates,obj -> {
                        onloading(obj);
                    });
                    binding.textView2.setText("Common word on day :"+dates.get(0));
                    binding.dateitem.setLayoutManager(new LinearLayoutManager(ComplaintAnalysisActivity.this,LinearLayoutManager.HORIZONTAL, false));
                    binding.dateitem.setAdapter(datedaysController);
                    onloading(dates.get(0));
                }

            }
        });
    }

    private void onloading(String date) {
        binding.textView2.setText("Common word on day :"+date);
        BarChart barChart=binding.idBarChart;
        barChart.clearChart();
        barChart.addBar(new BarModel(datacommonwords.get(date).get(0).getKey(),Float.valueOf((long)datacommonwords.get(date).get(0).getValue()),getResources().getColor(R.color.red)));
        barChart.addBar(new BarModel(datacommonwords.get(date).get(1).getKey(),Float.valueOf((long)datacommonwords.get(date).get(1).getValue()),getResources().getColor(R.color.teal_200)));
        barChart.addBar(new BarModel(datacommonwords.get(date).get(2).getKey(),Float.valueOf((long)datacommonwords.get(date).get(2).getValue()),getResources().getColor(R.color.teal_700)));
        barChart.addBar(new BarModel(datacommonwords.get(date).get(3).getKey(),Float.valueOf((long)datacommonwords.get(date).get(3).getValue()),getResources().getColor(R.color.bluefateh)));
        barChart.addBar(new BarModel(datacommonwords.get(date).get(4).getKey(),Float.valueOf((long)datacommonwords.get(date).get(4).getValue()),getResources().getColor(R.color.bluefateh)));
        barChart.addBar(new BarModel(datacommonwords.get(date).get(5).getKey(),Float.valueOf((long)datacommonwords.get(date).get(5).getValue()),getResources().getColor(R.color.bluefateh)));

        barChart.startAnimation();
    }
}