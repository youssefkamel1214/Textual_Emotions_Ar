package com.example.texttoemotion.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.texttoemotion.databinding.ActivityComplaintSummrizationBinding;

public class ComplaintSummrizationActivity extends AppCompatActivity {
    ActivityComplaintSummrizationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityComplaintSummrizationBinding.inflate(getLayoutInflater());
        binding.complaintbefore.setText("من موقعك الإلكتروني في 1 مارس 2023 ووعدت بأنه سيصل في غضون أسبوع واحد. ومع ذلك ، لم يصل الهاتف حتى الآن ولا يمكنني تتبع شحنته عبر الإنترنت.\n" +
                "\n" +
                "اتصلت بخدمة العملاء عدة مرات عبر الهاتف والبريد الإلكتروني ولم أحصل على أي رد مرضٍ. كان الموظفون غير مهذبين وغير مساعدين ولا يستطيعون إخباري بسبب التأخير أو موقف طلبي. شعرت بالإحباط والإهانة من قبل شركة تزعم أنها تقدم منتجات عالية الجودة وخدمة رائعة.\n" +
                "\n" +
                "أطالب بأن ترسل لي هاتفي في أقرب وقت ممكن أو ترجع لي المال كاملاً. إذا لم أستلم هاتفي أو استردادًا في غضون 10 أيام عمل ، فسأقوم بإجراء المزيد من الإجراءات القانونية ضد شركتك. هذه ليست طريقة للتعامل مع الزبائن المخلصين.\n" +
                "\n" +
                "أود أن تقوم بالتحقق من هذه المشكلة على وجه السرعة وألا تسمح لهذا الأمر بالحدوث مجددًا في المستقبل. إذا كان لديك أية استفسارات حول شكواى، يُرجى التواصل معى على رقم هاتفى 0123456789 أو عبر بریدی الإلکترو نی example@example.com.\n" +
                "\n" +
                "شكرًا لانتباهك إلى هذه المسألة.\n");
        binding.complaintafter.setText("أنا غاضب جدا من خدمة العملاء التي تلقيتها من شركتك منذ 1 مارس 2023 ووعدت بأنه سيصل في غضون أسبوع واحد إذا لم أستلم هاتفي أو استردادا في غضون 10 أيام عمل فسأقوم بإجراء المزيد من الإجراءات القانونية ضد شركتك هذه ليست طريقة للتعامل مع الزبائن المخلصين\n");
        setContentView(binding.getRoot());
    }
}