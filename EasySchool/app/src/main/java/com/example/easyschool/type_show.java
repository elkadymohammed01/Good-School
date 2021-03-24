package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easyschool.adapter.question_adapter;
import com.example.easyschool.data.Question;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.model.CStagged;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class type_show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_show);
        set_text();
        inf();
        identity();

    }

    private void identity( ) {

        linearLayout=(findViewById(R.id.item_picker));

    }
    //Personal Information
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }

    private int type =0;
    TextView add,show;
    LinearLayout linearLayout;

    void set_text(){
        show =findViewById(R.id.show);
        add =findViewById(R.id.add);
        show.setText("Show "+Save.getType());
        add.setText("Add "+Save.getType());
    }

    public void go(View view) {
        if(type==0){

            if(Save.getType().equals("Question"))
                startActivity(new Intent(getApplicationContext(),question_st.class));
            else if(Save.getType().equals("Activity"))
                startActivity(new Intent(getApplicationContext(),Activity_value.class));
            else if(Save.getType().equals("Lesson"))
                startActivity(new Intent(getApplicationContext(),nop.class));
            else if(Save.getType().equals("Task")) {

                Save.setShow(true);
                startActivity(new Intent(getApplicationContext(), CStagged.class));
            }

        }else{
            if(Save.getType().equals("Question"))
                startActivity(new Intent(getApplicationContext(),quesion_type.class));
            else if(Save.getType().equals("Activity"))
                startActivity(new Intent(getApplicationContext(),Add_Activity.class));
            else if(Save.getType().equals("Lesson"))
                startActivity(new Intent(getApplicationContext(),add_lesson.class));
            else if(Save.getType().equals("Task"))
                startActivity(new Intent(getApplicationContext(),add_task.class));
        }

        finish();
    }

    public void make_1(View view) {
        type=1;
        ImageView imageView=findViewById(R.id.image1);
        ImageView imageView2=findViewById(R.id.image2);
        imageView.setImageResource(R.color.white);
        imageView2.setImageResource(R.drawable.correct_answer);
    }

    public void make_0(View view) {
        type=0;
        ImageView imageView=findViewById(R.id.image1);
        ImageView imageView2=findViewById(R.id.image2);
        imageView2.setImageResource(R.color.white);
        imageView.setImageResource(R.drawable.correct_answer);
    }




}
