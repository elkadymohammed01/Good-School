package com.example.anew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anew.data.ArrayAdapterWithIcon;
import com.example.anew.data.myDbAdapter;
import com.example.anew.data.singletoon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    int id = 0;
    FirebaseDatabase database;
    DatabaseReference reference;
String momo="MokLop";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ImageView image; TextView logo,slogan;
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        //Animations 
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.set_top);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.set_left);
//Set animation to elements 
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        myDbAdapter mdod=new myDbAdapter(getApplicationContext());
        final String sop[]=mdod.getData().split(" ");
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        if(sop.length>=3)
            firebaseAuth.signInWithEmailAndPassword(sop[1],sop[2]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {

                        singletoon.mail = sop[1];
                        singletoon.name = sop[3];
                        singletoon.phone = sop[4];

                        reference = database.getInstance().getReference().child("note").child(singletoon.mail.substring(0,singletoon.mail.length()-4));
                        Query query=reference;
query.orderByChild("email").equalTo(singletoon.mail);
                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                momo = dataSnapshot.child("name").getValue().toString()+" ";
                                if (momo.isEmpty()){
                                    Toast.makeText(getApplicationContext(),"please fill ",Toast.LENGTH_LONG).show();
                                }else {
                                    notification();

                                }

                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);
                        finish();

                    }
                    else {Intent intent=new Intent(MainActivity.this,Main_log_in.class);
                        startActivity(intent);}

                }
            });
    }
    private void notification(){
singletoon.see++;
        String name = momo;
        String message = "See Your Information News Which U Post";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("n","n",NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentText("Code Sphere")
                .setSmallIcon(R.drawable.refer_icon)
                .setAutoCancel(true)
                .setContentText(name+"  " + message);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());

    }

}
