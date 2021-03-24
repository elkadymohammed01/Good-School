package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.easyschool.data.Check_Text;
import com.example.easyschool.data.Task;
import com.example.easyschool.data.myDbAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class add_task extends AppCompatActivity {

    private EditText title,details,lv;
    private Button add ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        inf();
        identity();
        on_post();
    }

    private void point(){
        FirebaseDatabase.getInstance().getReference().child("point")
                .child(email.substring(0,email.length()-4))
                .child("task").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int val=0;
                if(snapshot.exists()){
                    val=Integer.parseInt(snapshot.getValue().toString());
                }
                val++;
                FirebaseDatabase.getInstance().getReference().child("point")
                        .child(email.substring(0,email.length()-4))
                        .child("task").setValue(val);

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
                 Task task =new Task();
                 task.setTitle(title.getText().toString());
                 task.setDetails(details.getText().toString());
                 task.setLevel(lv.getText().toString());
                 task.setSubject(level);
                 task.setTeacher_email(email);
                    FirebaseDatabase.getInstance().getReference().child("Task").push().setValue(task);
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
