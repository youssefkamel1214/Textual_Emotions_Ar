package com.example.texttoemotion.Home;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.texttoemotion.Constants;
import com.example.texttoemotion.controller.ComplaintAdapter;
import com.example.texttoemotion.databinding.FragmentHomeBinding;
import com.example.texttoemotion.databinding.ReviewalertboxBinding;
import com.example.texttoemotion.models.Complaint;
import com.example.texttoemotion.models.Feedback;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView.SmoothScroller smoothScroller;
    int index=0;
    ComplaintAdapter complaintAdapter;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    Thread runningThread;
    private SimpleDateFormat DMY=new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");


    String tag="HomeFragment";
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        getComplaints();
//        Complaint[] complaints = new Complaint[10];
//        complaints[0] = new Complaint("ضرائب مرتفعة", "الضرائب مرتفعة وغير معقولة. أريد خفض الضرائب.");
//        complaints[1] = new Complaint("مسؤولون فاسدون", "المسؤولون فاسدون وغير أمينين. يأخذون الرشاوى ويسيئون استخدام سلطتهم.");
//        complaints[2] = new Complaint("طرق سيئة", "الطرق سيئة وغير آمنة. لديها حفر وشقوق. تحتاج إلى إصلاح.");
//        complaints[3] = new Complaint("بيروقراطية بطيئة", "البيروقراطية بطيئة وغير فعالة. تستغرق وقتًا طويلًا في معالجة مستنداتي وطلباتي.");
//        complaints[4] = new Complaint("تعليم رديء", "نظام التعليم رديء وغير كافٍ. لا يوفرون تعليمًا وموارد عالية الجودة.");
//        complaints[5] = new Complaint("نقص في الأمن", "الوضع الأمني ضعيف ومقلق. هناك جريمة وعنف في كل مكان. تحتاج إلى تطبيق القانون والنظام.");
//        complaints[6] = new Complaint("بيئة ملوثة", "البيئة ملوثة وغير صحية. هناك دخان وقمامة في كل مكان. تحتاج إلى حماية البيئة.");
//        complaints[7] = new Complaint("سياسات غير عادلة", "السياسات غير عادلة وظالمة. تفضل بعض المجموعات على أخرى. تحتاج إلى أن تكون أكثر شمولية وإنصافًا.");
//        complaints[8] = new Complaint("زعماء غير أكفاء", "الزعماء غير أكفاء ولا يفهمون شيئًا. لا يمتلكون رؤية أو استراتيجية. تحتاج إلى استبدالهم.");
//        complaints[9] = new Complaint("انتهاك الحقوق", "الحكومة تنتهك حقوقي وحرياتي. تراقبني وتخبر علَّىَّ . تحتاج إلى احترام خ") ;
        complaintAdapter = new ComplaintAdapter(new ArrayList<Complaint>());
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

        binding.floatactionbutton.setOnClickListener(v -> {
            showReviewDialog();
        });
        return binding.getRoot();

    }

    private void getComplaints() {
        runningThread= new Thread(() -> {
            ArrayList<Complaint>complaints=new ArrayList<Complaint>();
            Task<QuerySnapshot>task =db.collection("complaints").whereEqualTo("userid", FirebaseAuth.getInstance().getUid()).get();
            try {
                Tasks.await(task);
                getActivity().runOnUiThread(() -> {
                    if(task.isSuccessful()){
                        for (int i=0;i<task.getResult().getDocuments().size();i++){
                            DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(i);
                            complaints.add(documentSnapshot.toObject(Complaint.class));
                        }
                    }
                    if(complaints.isEmpty()){
                        binding.right.setVisibility(View.GONE);
                        binding.leftscrool.setVisibility(View.GONE);
                    }
                    else {
                        complaintAdapter.sumbitComplaints(complaints);
                        binding.emptytext.setVisibility(View.GONE);
                        binding.right.setVisibility(View.VISIBLE);
                        binding.leftscrool.setVisibility(View.VISIBLE);
                    }
                });

            } catch (ExecutionException e) {

                getActivity().runOnUiThread(() -> {
                    Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                });
            } catch (InterruptedException e) {
//                Log.e(tag,e.getMessage());
            }
        });
        runningThread.start();
    }

    private void showReviewDialog() {
        ReviewalertboxBinding reviewalertboxBinding= ReviewalertboxBinding.inflate(getLayoutInflater());
        Dialog dialog=new Dialog(requireContext());
        dialog.setContentView(reviewalertboxBinding.getRoot());
        reviewalertboxBinding.submit.setOnClickListener(v -> {
            Float review=reviewalertboxBinding.ratingBar.getRating();
            if(review!=0.0){
                uploadFeedback(review,reviewalertboxBinding.feedbacktext.getText().toString(),dialog);
            }else {
                Toast.makeText(requireContext(),"Please provide a rating",Toast.LENGTH_LONG).show();
            }
        });
        binding.leftscrool.setVisibility(View.GONE);
        binding.right.setVisibility(View.GONE);

        dialog.setOnDismissListener(dialog1 -> {
            binding.leftscrool.setVisibility(View.VISIBLE);
            binding.right.setVisibility(View.VISIBLE);
        });
        dialog.show();
    }

    private void uploadFeedback(Float review, String text, Dialog dialog) {
        String id= db.collection("feedbacks").document().getId();
        Calendar calendar=Calendar.getInstance();
        Feedback feedback=new Feedback(text,review,id, FirebaseAuth.getInstance().getUid(),DMY.format(calendar.getTime()));
        new Thread(() -> {
           Task<Void> task = db.collection("feedbacks").document(feedback.getId()).set(feedback);
            try {
                Tasks.await(task);
            } catch (ExecutionException | InterruptedException e) {
                Log.e(tag, e.getMessage());
            }
            getActivity().runOnUiThread(() -> {
                if (!task.isSuccessful()){
                    Toast.makeText(requireContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            });
        }).start();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        runningThread.interrupt();
        binding = null;
    }

}