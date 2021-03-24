package com.example.task_mlt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.task_mlt.data.myDbAdapter;
import com.example.task_mlt.data.singletoon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        Button Be=findViewById(R.id.be);
        Be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_data();   Intent intent =new Intent(getApplicationContext(),Main_Navigation.class);startActivity(intent);
            }
        });
    }
void get_data(){
    myDbAdapter mdod=new myDbAdapter(getApplicationContext());
    final String sop[]=mdod.getData().split(" ");
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    if(sop.length>3)
    firebaseAuth.signInWithEmailAndPassword(sop[1],sop[2]).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()) {
                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Users").child(sop[1].substring(0, sop[1].length() - 4));
                Query query = dbr;
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DataSnapshot data = snapshot;
                        singletoon.mail = sop[1];
                        singletoon.name = sop[3];
                        singletoon.phone = sop[4];
                        if (snapshot.exists() ) {
                            singletoon.name=data.child("userName").getValue().toString();
                            singletoon.phone=data.child("phone").getValue().toString();
                           Intent intent =new Intent(getApplicationContext(),Main_Navigation.class);startActivity(intent);
                            finish();}
                        else { Toast.makeText(getApplicationContext(), "Error in Email or Password", Toast.LENGTH_LONG).show(); }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else {
                Intent intent=new Intent(MainActivity.this,log_in.class);
                startActivity(intent);}

        }
    });
    else {
        Intent intent=new Intent(MainActivity.this,log_in.class);
        startActivity(intent);}
}
}



