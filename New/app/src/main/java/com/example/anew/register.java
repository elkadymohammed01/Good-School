package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        Button button =(Button)findViewById(R.id.cirLoginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name=((EditText)findViewById(R.id.editTextName)).getText().toString();
                final String phone = ((EditText) findViewById(R.id.editTextMobile)).getText().toString();
                final String email=((EditText)findViewById(R.id.editTextEmail)).getText().toString();
                final String password=((EditText)findViewById(R.id.editTextPassword)).getText().toString();
                final FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Check Your Email",Toast.LENGTH_LONG).show();
                                    if(email.length()>4)
                                        writeNewUser(FirebaseDatabase.getInstance().getReference(),email.substring(0,email.length()-4),name,email,password,phone);

                                    finish();}
                                else
                                    Toast.makeText(getApplicationContext(),"Please try Again", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });


    }
    public void writeNewUser(DatabaseReference databaseReference, String userId, String name, String email, String password, String phone) {
        User user = new User(name, email, password,phone);

        databaseReference.child("Users").child(userId).setValue(user);
    }
}