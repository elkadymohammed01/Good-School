package com.example.task_mlt.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class firebase_methods {
    public static Bitmap getImage(String path){
        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(path);
        final Bitmap[] bitmap = new Bitmap[1];
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new
                                                                      OnSuccessListener<byte[]>() {
                                                                          @Override
                                                                          public void onSuccess(byte[] bytes) {
                                                                             bitmap[0] = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                                                                              }
                                                                      }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
return bitmap[0];
    }
    public static void addImage(String path,String path2, Context context,Uri file){
        Uri filePath=file;
        if (filePath != null) {
            FirebaseStorage storage;
            StorageReference storageReference;;
             StorageReference mStorageRef;
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference storageRef = storage.getReference();
            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(path+"/"+path2);

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();

                                    // Error, Image not uploaded
                                    progressDialog.dismiss();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {


                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }

    }
public static void add_server (Event event, String id){
        if(event.id.length()==0)
        event.id=id;
event.manger=singletoon.name;
        FirebaseDatabase.getInstance().getReference().child("Event").child(id).setValue(event);
    }
public static ArrayList<Event> get_events(final Context context){
        final ArrayList<Event> events =new ArrayList<>();
    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Event");
    Query query = dbr;
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot dd:snapshot.getChildren()){
                Event event =new Event();
                event.name=dd.child("name").getValue().toString();

                event.email=dd.child("email").getValue().toString();
                event.information=dd.child("information").getValue().toString();
                event.manger=dd.child("manger").getValue().toString();
                event.time=dd.child("time").getValue().toString();
                event.id=dd.child("id").getValue().toString();
                events.add(event);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    });
return events;
    }
}
