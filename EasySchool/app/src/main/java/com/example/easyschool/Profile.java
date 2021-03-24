package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyschool.data.Save;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.data.op_save;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    TextView Name,Email,lv;
    ImageView imageView;
    TextView task,point,activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        inf();
        identity();
        add_information();
        information();
    }

    private void identity() {
        Name = findViewById(R.id.user_name);
        Email = findViewById(R.id.user_email);
        lv = findViewById(R.id.level);
        task = findViewById(R.id.task_done);
        activity = findViewById(R.id.activity_done);
        point = findViewById(R.id.pon_add);
        imageView=findViewById(R.id.user_image);
    }

    private void add_information(){
        lv.setText("Level "+level);
        Name.setText(name);
        Email.setText(email);
        op_save op=new op_save();
        op.download_file(email,imageView);
    }


    private void information(){
        FirebaseDatabase.getInstance().getReference().child("point")
                .child(email.substring(0,email.length()-4))
                .child("question").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    point.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("point")
                .child(email.substring(0,email.length()-4))
                .child("activity").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    activity.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("point")
                .child(email.substring(0,email.length()-4))
                .child("task").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    task.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }

    public void mailing(View view) {

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ Save.getUser().getEmail()});
        email.putExtra(Intent.EXTRA_SUBJECT, "");
        email.putExtra(Intent.EXTRA_TEXT, "");

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));

    }

    public void calling(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+Save.getUser().getPhone()));

        if (ActivityCompat.checkSelfPermission(Profile.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }
}
