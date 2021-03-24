package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.easyschool.data.Activities;
import com.example.easyschool.data.Check_Text;
import com.example.easyschool.data.myDbAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add_Activity extends AppCompatActivity {
    private EditText title,details,lv;
    private Button add ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        inf();
        identity();
        on_post();
    }

    private void point(){
        FirebaseDatabase.getInstance().getReference().child("point")
                .child(email.substring(0,email.length()-4))
                .child("activity").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int val = Integer.parseInt(snapshot.getValue().toString())+1;
                    FirebaseDatabase.getInstance().getReference().child("point")
                            .child(email.substring(0,email.length()-4))
                            .child("activity").setValue(val+"");
                }
                else{FirebaseDatabase.getInstance().getReference().child("point")
                        .child(email.substring(0,email.length()-4))
                        .child("activity").setValue("1");}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void on_post(){
        final Check_Text checkText=new Check_Text(getApplicationContext());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkText.is_empty(title)&&
                        !checkText.is_empty(details)&&
                        !checkText.is_empty(lv)&&
                        !checkText.is_Integer(level)){
                    Activities activity =new Activities();
                    activity.setTitle(title.getText().toString());
                    activity.setDetails(details.getText().toString());
                    activity.setLevel(lv.getText().toString());
                    activity.setTeacher_email(email);
                    activity.setTeacher(name);
                    activity.setTeacher_email(email);
                    activity.setTime(checkText.getTime());
                    point();
                    FirebaseDatabase.getInstance().getReference().child("Activity").push().setValue(activity);
                    finish();
                }
            }

        });
    }

    private void identity(){
        title=findViewById(R.id.ts_title);
        details=findViewById(R.id.ts_details);
        lv=findViewById(R.id.ts_level);
        add=findViewById(R.id.post);
    }
    private String name,phone,email,level;

    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }
}
