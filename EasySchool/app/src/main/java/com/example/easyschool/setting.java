package com.example.easyschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.easyschool.data.Save;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.model.CStagged;

import java.util.ArrayList;

public class setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);
        put_id();
        identity();
        inf();
        if(level.length()>0&&level.charAt(0)>'9'){
        show_all();}
        else{
            asStudent();
        }
    }


    private ArrayList<Integer> id=new ArrayList<>();
    private ArrayList<String> search=new ArrayList<>();
    private ArrayList<CardView> card=new ArrayList<>();
    private String name,phone,email,level="0";
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];
        level=all[3];
    }

    private void asStudent() {
        for (int i=0;i<card.size();i++){
            final int finalI = i;

        }
        card.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save.setType("all");
                startActivity(new Intent(getApplicationContext(), CStagged.class));
            }
        });
        card.get(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save.setType("all");
                startActivity(new Intent(getApplicationContext(), Activity_value.class));
            }
        });
        card.get(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), question_st.class));
            }
        });
        card.get(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), nop.class));
            }
        });
    }
    private void put_id(){
        id.add(R.id.card_setting);id.add(R.id.card_student);id.add(R.id.card_task);
        id.add(R.id.card_teacher);id.add(R.id.card_lesson);
        id.add(R.id.card_activity);id.add(R.id.card_questions);
        search.add("Setting");search.add("Student");search.add("Task");
        search.add("Teacher");search.add("Lesson");
        search.add("Activity");search.add("Question");
    }

    private void identity() {
        for (int i=0;i<id.size();i++){
            card.add((CardView) findViewById(id.get(i)));
        }
    }

    public void show_all() {
        for (int i=0;i<card.size();i++){
            final int finalI = i;
            card.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),type_show.class));
                    Save.setType(search.get(finalI));
                }
            });
        }

    }

    public void user_setting(View view) {
        Intent user_Setting=new Intent(getApplicationContext(),setting_user.class);
        startActivity(user_Setting);
    }

    public void back(View view) {
        finish();
    }

    public void get_st(View view) {
        Save.setType("0");
        Intent user_Setting=new Intent(getApplicationContext(),person.class);
        startActivity(user_Setting);
    }

    public void get_teacher(View view) {
        Save.setType("1");
        Intent user_Setting = new Intent(getApplicationContext(),person.class);
        startActivity(user_Setting);
    }

    public void get_contact(View view) {
        Save.setType("2");
        Intent user_Setting = new Intent(getApplicationContext(),person.class);
        startActivity(user_Setting);
    }
}
