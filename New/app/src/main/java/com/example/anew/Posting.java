package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.anew.data.Server;
import com.example.anew.data.singletoon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.security.acl.Group;
import java.sql.Time;

public class Posting extends AppCompatActivity {
String inf , Title;
long Time;
ImageView photo;
    private Bitmap bitmap=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);
        Button go=findViewById(R.id.post);
getSupportActionBar().hide();
        photo=findViewById(R.id.photo);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inf=((EditText)findViewById(R.id.editText)).getText().toString();
                Title=((EditText)findViewById(R.id.editText2)).getText().toString();
                Time=Long.MAX_VALUE-System.nanoTime();
                RadioGroup rg=findViewById(R.id.gbo);
               RadioButton rb=findViewById(rg.getCheckedRadioButtonId());
               if(Title.length()<=20&&Title.split(" ").length<=3) {
                   if (inf.length() > 12 && Title.length() >= 3 && bitmap != null) {

                       writeNewServer(FirebaseDatabase.getInstance().getReference(),
                               String.valueOf(Time), Title, singletoon.mail, inf, rb.getText().toString());

                       uploadImage(String.valueOf(Time));
                       finish();
                   } else
                       Toast.makeText(getApplicationContext(), "Check Title information And photo", Toast.LENGTH_LONG).show();
               }
               else{
                   if(Title.length()<=20)
                   Toast.makeText(getApplicationContext(), "The Title Should By 20 character At most", Toast.LENGTH_LONG).show();
                   else
                       Toast.makeText(getApplicationContext(), "The Title Should By 3 word At most", Toast.LENGTH_LONG).show();

               }
            }
        });

    }
    int a[]=new int[131];
    Server server;
    String lop="";
    public void writeNewServer(DatabaseReference databaseReference, String userId, String name, String email, String inf,String type) {
        server=new Server(name,inf,email,type);
        server.user=singletoon.name;
        databaseReference.child("Server").child(userId).setValue(server);
        for(String S: name.toLowerCase().split(" "))
            FirebaseDatabase.getInstance().getReference().child("Search").child(S).child(userId).setValue(server);
    }

    FirebaseStorage storage;
    StorageReference storageReference;
    Uri file=null ;

    private StorageReference mStorageRef;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                file=targetUri;
                //             textTargetUri.setText(targetUri.toString());
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    photo.setImageBitmap(bitmap);
                    this.bitmap=bitmap;
                    String i1 = bitmap.toString();
                    Log.i("firstimage........", "" + i1);
                    int x=0;
                    photo.setVisibility(x);

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(this,"Support Error", Toast.LENGTH_SHORT).show();
                } } } }
    private void uploadImage(String Server_name)
    {
        Uri filePath=file;
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
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
