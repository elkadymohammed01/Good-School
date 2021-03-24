package com.example.anew.ui.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.anew.Gallery;
import com.example.anew.Massage;
import com.example.anew.News_information;
import com.example.anew.Posting;
import com.example.anew.R;
import com.example.anew.data.Server;
import com.example.anew.data.myDbAdapter;
import com.example.anew.data.singletoon;
import com.example.anew.notefication;
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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ProfileFragment extends Fragment {

    ImageView photo;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_profile, container, false);

        photo=root.findViewById(R.id.me);
        TextView m_shop=root.findViewById(R.id.mshop);
m_shop.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(getContext(), Gallery.class));
    }
});
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final TextView love_all=root.findViewById(R.id.love_all),view_all=root.findViewById(R.id.view_all);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("love_all").child(singletoon.mail.substring(0,singletoon.mail.length()-4));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    if(snapshot.getValue().toString().length()<=3)
                        love_all.setText(snapshot.getValue().toString());
                    else  love_all.setText(snapshot.getValue().toString().charAt(0)+"."+snapshot.getValue().toString().charAt(1)+"K");

                else{love_all.setText("0");}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference().child("view_all").child(singletoon.mail.substring(0,singletoon.mail.length()-4));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    if(snapshot.getValue().toString().length()<=3)
                        view_all.setText(snapshot.getValue().toString());
                    else  view_all.setText(snapshot.getValue().toString().charAt(0)+"."+snapshot.getValue().toString().charAt(1)+"K");

                else{view_all.setText("0");}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        final myDbAdapter myone=new myDbAdapter(getContext());
        try{
            String []col=myone.getData_inf();
            singletoon.mail=col[1];
            singletoon.name=col[0];
            singletoon.phone=col[2];}
        catch (Exception ex){
            Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_LONG).show();
        }
        StorageReference islandRef = storageRef.child(singletoon.mail+"/"+"me");

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new
                                                                      OnSuccessListener<byte[]>() {
                                                                          @Override
                                                                          public void onSuccess(byte[] bytes) {
                                                                              Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                                                              if(bitmap!=null)
                                                                                  photo.setImageBitmap(bitmap);
                                                                              else photo.setImageDrawable(root.getResources().getDrawable(R.drawable.smiley));
                                                                          }
                                                                      }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();

            }


            private void openGallery() {

                Intent cameraIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent,100);


            }
        });


        final TextView name=root.findViewById(R.id.nameTextView),
                phone=root.findViewById(R.id.phoneTextView),
                mail= root.findViewById(R.id.mailTextView),
                momail= root.findViewById(R.id.momail),
                moname= root.findViewById(R.id.moname);
        name.setText(singletoon.name);momail.setText(singletoon.mail);
        moname.setText(singletoon.name);phone.setText(singletoon.phone);mail.setText(singletoon.mail);
        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            if (resultCode == Activity.RESULT_OK) {

                //             textTargetUri.setText(targetUri.toString());
                try {
                    Uri targetUri = data.getData();
                    file=targetUri;
                    Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(targetUri));
                    photo.setImageBitmap(bitmap);
                    this.bitmap=bitmap;
                    String i1 = bitmap.toString();
                    Log.i("firstimage........", "" + i1);
                    int x=0;
                    photo.setVisibility(x);
                    uploadImage("me");
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getContext(),"Support Error", Toast.LENGTH_SHORT).show();
                } } } }
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri file=null ;
    Bitmap bitmap=null;
    private StorageReference mStorageRef;

    private void uploadImage(String Server_name)
    {
        Uri filePath=file;
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(singletoon.mail+"/"+Server_name);

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
}

