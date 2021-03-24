package com.example.task_mlt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class log_in extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();
        TextView textView = (TextView) findViewById(R.id.to_register);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(log_in.this, register.class);
               // startActivity(intent);
            }
        });
        Button button = (Button) findViewById(R.id.cirloginbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail = ((EditText) findViewById(R.id.editTextEmail)).getText().toString(),
                        password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
                if (mail.length() > 4 && password.length() > 0) {
                    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                add_to_data(mail,password);
                                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Users").child(mail.substring(0, mail.length() - 4));
                                Query query = dbr;
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        DataSnapshot data = snapshot;
                                        singletoon.mail=mail;

                                        if (snapshot.exists() && data.child("password").getValue().toString().equals(password)) {
                                            myDbAdapter myone=new myDbAdapter(getApplicationContext());
                                            singletoon.name=data.child("userName").getValue().toString();
                                            singletoon.phone=data.child("phone").getValue().toString();
                                            long x=myone.insertData(singletoon.name,singletoon.phone,singletoon.mail,password);
                                            //Toast.makeText(getApplicationContext(),String.valueOf(x),Toast.LENGTH_LONG).show();
                                          //  Intent intent =new Intent(log_in.this,Main_page.class);startActivity(intent);
                                            finish();}
                                        else { Toast.makeText(getApplicationContext(), "Error in Email or Password", Toast.LENGTH_LONG).show(); }
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
        });
    }
    void add_to_data(String mail, String password){
        myDbAdapter mdod=new myDbAdapter(getApplicationContext());
        mdod.insertData(mail,password);
    }
}