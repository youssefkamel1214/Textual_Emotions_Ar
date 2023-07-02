package com.example.texttoemotion.controller;

import com.example.texttoemotion.models.Complaint;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;

import java.util.ArrayList;

public class ComplaintListener {
    private ListenerRegistration listenerRegistration;
    private Callback<Boolean>loadingIndicator;
    private Callback<ArrayList<Complaint>>complaintscallback;
    private static ComplaintListener instance;
    private ArrayList<Complaint>complaints;
   private ComplaintListener(FirebaseFirestore db, Callback<Boolean>loadingIndicator, Callback<ArrayList<Complaint>>complaintscallback){
       this.loadingIndicator=loadingIndicator;
       this.complaintscallback=complaintscallback;
       if(loadingIndicator!=null)
        loadingIndicator.call(true);
       listenerRegistration= db.collection("complaints").addSnapshotListener(command -> new Thread(command).start(), MetadataChanges.INCLUDE,(value, error) -> {
            ArrayList<Complaint>arrayList=new ArrayList<>();
            if(loadingIndicator!=null)
                loadingIndicator.call(true);
            for (DocumentSnapshot doc:value.getDocuments()) {
                arrayList.add(doc.toObject(Complaint.class));
            }
           if(loadingIndicator!=null)
               loadingIndicator.call(false);
           if(complaintscallback!=null)
               complaintscallback.call(arrayList);
           complaints=arrayList;
        });
   }

   public void clearlithners(){
       loadingIndicator=null;
       complaintscallback=null;
   }
   public static ComplaintListener getInstance(FirebaseFirestore db, Callback<Boolean>loadingIndicator, Callback<ArrayList<Complaint>>complaintscallback){
       if (instance==null){
           instance=new ComplaintListener(db,loadingIndicator,complaintscallback);
       }
       else{
            instance.loadingIndicator= loadingIndicator;
            instance.complaintscallback=complaintscallback;
       }
       return instance;
   }

    public ArrayList<Complaint> getComplaints() {
        return complaints;
    }
}
