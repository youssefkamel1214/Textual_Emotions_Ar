package com.example.texttoemotion.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.texttoemotion.controller.ComplaintAdapter;
import com.example.texttoemotion.databinding.FragmentHomeBinding;
import com.example.texttoemotion.models.Complaint;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView.SmoothScroller smoothScroller;
    int index=0;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Complaint[] complaints = new Complaint[10];
        complaints[0] = new Complaint("ضرائب مرتفعة", "الضرائب مرتفعة وغير معقولة. أريد خفض الضرائب.");
        complaints[1] = new Complaint("مسؤولون فاسدون", "المسؤولون فاسدون وغير أمينين. يأخذون الرشاوى ويسيئون استخدام سلطتهم.");
        complaints[2] = new Complaint("طرق سيئة", "الطرق سيئة وغير آمنة. لديها حفر وشقوق. تحتاج إلى إصلاح.");
        complaints[3] = new Complaint("بيروقراطية بطيئة", "البيروقراطية بطيئة وغير فعالة. تستغرق وقتًا طويلًا في معالجة مستنداتي وطلباتي.");
        complaints[4] = new Complaint("تعليم رديء", "نظام التعليم رديء وغير كافٍ. لا يوفرون تعليمًا وموارد عالية الجودة.");
        complaints[5] = new Complaint("نقص في الأمن", "الوضع الأمني ضعيف ومقلق. هناك جريمة وعنف في كل مكان. تحتاج إلى تطبيق القانون والنظام.");
        complaints[6] = new Complaint("بيئة ملوثة", "البيئة ملوثة وغير صحية. هناك دخان وقمامة في كل مكان. تحتاج إلى حماية البيئة.");
        complaints[7] = new Complaint("سياسات غير عادلة", "السياسات غير عادلة وظالمة. تفضل بعض المجموعات على أخرى. تحتاج إلى أن تكون أكثر شمولية وإنصافًا.");
        complaints[8] = new Complaint("زعماء غير أكفاء", "الزعماء غير أكفاء ولا يفهمون شيئًا. لا يمتلكون رؤية أو استراتيجية. تحتاج إلى استبدالهم.");
        complaints[9] = new Complaint("انتهاك الحقوق", "الحكومة تنتهك حقوقي وحرياتي. تراقبني وتخبر علَّىَّ . تحتاج إلى احترام خ") ;
        ComplaintAdapter complaintAdapter=new ComplaintAdapter(new ArrayList<Complaint>(Arrays.asList(complaints)));
        binding.reyclerView.setAdapter(complaintAdapter);
        smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int calculateTimeForScrolling(int dx) {
                return 250;
            }

            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_END;
            }
        };
        binding.reyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        binding.leftscrool.setOnClickListener(v -> {
            if(index!=0)
               index--;
            smoothScroller.setTargetPosition(index);
            binding.reyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
        });
        binding.right.setOnClickListener(v -> {
            if(index+1!=complaintAdapter.getItemCount())
                index++;
            smoothScroller.setTargetPosition(index);
            binding.reyclerView.getLayoutManager().startSmoothScroll(smoothScroller);

        });
        binding.buttonFirst.setOnClickListener(v -> {
            Toast.makeText(getContext(),"index clicked"+Integer.toString(index),Toast.LENGTH_LONG).show();
        });
        return binding.getRoot();

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