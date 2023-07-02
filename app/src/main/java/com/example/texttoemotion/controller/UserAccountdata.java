package com.example.texttoemotion.controller;

import com.example.texttoemotion.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.ExecutionException;

public class UserAccountdata {
     private User currentUser;
     private static UserAccountdata instance;
    private UserAccountdata(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public static UserAccountdata getInstance(FirebaseAuth firebaseAuth, FirebaseFirestore db,Callback<Exception>callback){
        if(instance==null) {
            Task<DocumentSnapshot> userdoc = db.collection("users").document(firebaseAuth.getUid()).get();
            try {
                Tasks.await(userdoc);
                if (userdoc.isSuccessful()) {
                    User currentUser = userdoc.getResult().toObject(User.class);
                    instance = new UserAccountdata(currentUser);
                } else {
                    throw new Exception(userdoc.getException().getMessage());
                }
            } catch (Exception e) {
                callback.call(e);
            }
        }
        return  instance;
    }
}
