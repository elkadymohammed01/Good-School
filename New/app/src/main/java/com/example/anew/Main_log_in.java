package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anew.data.myDbAdapter;
import com.example.anew.data.singletoon;
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

public class Main_log_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_log_in);
        TextView make = findViewById(R.id.make_one);
        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_log_in.this, register.class));
            }
        });
        final Button log_in=findViewById(R.id.cirLoginButton);
        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_in();
            }
        });
    }
        public void log_in() {
            final String mail = ((EditText) findViewById(R.id.editTextEmail)).getText().toString(),
                    password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
            if (mail.length() > 4 && password.length() > 0) {
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Users").child(mail.substring(0, mail.length() - 4));
                            Query query = dbr;
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    DataSnapshot data = snapshot;
                                    singletoon.mail=mail;
                                    myDbAdapter myone=new myDbAdapter(getApplicationContext());
                                    singletoon.name=data.child("userName").getValue().toString();
                                    singletoon.phone=data.child("phone").getValue().toString();
                                    long x=myone.insertData(singletoon.name,singletoon.phone,singletoon.mail,password);
                                    Toast.makeText(getApplicationContext(),String.valueOf(x), Toast.LENGTH_LONG).show();
                                    Intent intent =new Intent(getApplicationContext(),Main.class);startActivity(intent);
                                    finish();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });

            }
        }

    }