package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.texttoemotion.R;
import com.example.texttoemotion.databinding.ActivityComplaintAnalysisBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
                for (DocumentSnapshot documentSnapshot:
                     task.getResult().getDocuments()) {
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
                }

            }
        });
        onloading();
    }

    private void onloading() {
        BarChart barChart=binding.idBarChart;
        barChart.addBar(new BarModel("love mahmoud",Float.valueOf((int)4),getResources().getColor(R.color.red)));
        barChart.addBar(new BarModel("love borai",Float.valueOf((int)0),getResources().getColor(R.color.red)));
        barChart.addBar(new BarModel("love zeka",Float.valueOf((int)6),getResources().getColor(R.color.red)));
        barChart.addBar(new BarModel("love zeka",Float.valueOf((int)6),getResources().getColor(R.color.red)));
//        barChart.addBar(new BarModel("love zeka",Float.valueOf((int)6),getResources().getColor(R.color.red)));
//        barChart.addBar(new BarModel("love zeka",Float.valueOf((int)6),getResources().getColor(R.color.red)));
//        barChart.addBar(new BarModel(Float.valueOf((int)7),getResources().getColor(R.color.red)));
        barChart.startAnimation();
    }
}