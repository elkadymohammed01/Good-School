package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyschool.data.Save;
import com.example.easyschool.data.myDbAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setting_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user);
        inf();
        identity();
    }
    //Personal Information
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }
    TextView User_Name;
    // Item Of Page
    private void identity() {
        User_Name=findViewById(R.id.usernameTextView);
        set_def();
    }

    private void set_def(){
        User_Name.setText(name);
    }

    public void edit_User(View view) {
        startActivity(new Intent(getApplicationContext(),edit_user.class));
    }

    public void profile(View view) {
        startActivity(new Intent(getApplicationContext(),Profile.class));
    }

    public void follow(View view) {
        Save.setType("2");
        startActivity(new Intent(getApplicationContext(),person.class));
    }

    public void out(View view) {
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        mydb.delete(name);
        finishAffinity();
    }

    public void Change_password(View view) {
        showDialog(this);
    }

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.password_change);
        dialog.show();

        TextView ok =dialog.findViewById(R.id.ok);
        final EditText answer =dialog.findViewById(R.id.answer);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answer.getText().toString().length()>=6){

                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(email.substring(0,email.length()-4))
                            .child("password").setValue(answer.getText().toString());
                    Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
                    dialog.cancel();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Password is too short",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
