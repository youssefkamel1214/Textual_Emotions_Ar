package com.example.texttoemotion.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.texttoemotion.models.Complaint;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.FilenameUtils;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.lang.ref.WeakReference;

public class UploadComplaint extends AsyncTask<Complaint,Long,Object> {
    WeakReference<Context>contextWeakReference;
    WeakReference<Callback<Exception>>callbackWeakReference;
    FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    public UploadComplaint(Context context,Callback<Exception>callback) {
        this.contextWeakReference=new WeakReference<>(context);
        this.callbackWeakReference=new WeakReference<>(callback);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Callback<Exception> callback=callbackWeakReference.get();
        if(o instanceof  Exception)
           callback.call((Exception) o );
        else
            callback.call(null);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context=contextWeakReference.get();
        if(context==null) {
            cancel(false);
            return;
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Uploading file...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Complaint... complaints) {
        UploadTask uploadTask=null;
        if(!complaints[0].getFileurl().isEmpty()) {
            File f = new File(complaints[0].getFileurl());
            StorageReference reference = firebaseStorage.getReference().child("complaint attachment").child(complaints[0].getTitle() + "ssn" +
                    complaints[0].getUserid() + FilenameUtils.getExtension(f.getName()));
            uploadTask = reference.putFile(Uri.fromFile(f));
            uploadTask.addOnProgressListener(snapshot -> {
                publishProgress((snapshot.getBytesTransferred() * 100) / snapshot.getTotalByteCount());
            });
        }
        try {
            if(uploadTask!=null ){
                Tasks.await(uploadTask);
            }
            if (uploadTask==null||uploadTask.isSuccessful()) {

                // Get download url from task snapshot

                Uri uri;
                if(uploadTask!=null) {
                    uri = Tasks.await(uploadTask.getSnapshot()
                            .getMetadata()
                            .getReference()
                            .getDownloadUrl());
                    complaints[0].setFileurl(uri.toString());
                }
                Task<DocumentReference> colref= db.collection("complaints").add(complaints[0]);
                Tasks.await(colref);
                if(colref.isSuccessful())
                    return "finished";
                else
                    return   colref.getException();
            } else {

                return uploadTask.getException();
            }
        }catch (Exception e){
                      return  e;
        }
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        if(progressDialog!=null){
            progressDialog.setProgress(values[0].intValue());
        }
    }

    @Override
    protected void onCancelled(Object s) {
        super.onCancelled(s);
        progressDialog.dismiss();
        progressDialog=null;
    }
}
