package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.anew.data.ArrayAdapterWithIcon;
import com.example.anew.data.singletoon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class News_information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_information);
        TextView title = findViewById(R.id.title_news_item),
                information = findViewById(R.id.info_news_item),
                view=findViewById(R.id.views),
                person=findViewById(R.id.by_p)
                ,name=findViewById(R.id.by_name);
        title.setText(singletoon.server.name);
        name.setText(singletoon.server.user);
        information.setText(singletoon.server.information);
        view.setText(singletoon.server.view);
        person.setText(singletoon.server.email);
        final ImageView imageView=findViewById(R.id.imageView),chef_ph=findViewById(R.id.chef_ph);

        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference islandRef = storageRef.child(singletoon.server.email+"/"+singletoon.id);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new
                                                                      OnSuccessListener<byte[]>() {
                                                                          @Override
                                                                          public void onSuccess(byte[] bytes) {
                                                                              Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                                                                            imageView.setImageBitmap(bitmap); }
                                                                      }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
        islandRef = storageRef.child(singletoon.server.email+"/me");

        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new
                                                                      OnSuccessListener<byte[]>() {
                                                                          @Override
                                                                          public void onSuccess(byte[] bytes) {
                                                                              Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                                                                              chef_ph.setImageBitmap(bitmap); }
                                                                      }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });
        getSupportActionBar().hide();

    }
}





