package com.example.easyschool.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyschool.R;
import com.example.easyschool.adapter.CourseRecyclerAdapter;
import com.example.easyschool.adapter.CoursesItemClickListener;
import com.example.easyschool.data.DotsView;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.Task;
import com.example.easyschool.data.User;
import com.example.easyschool.data.lesson;
import com.example.easyschool.data.myDbAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CStagged extends AppCompatActivity
        implements CoursesItemClickListener {
    private CourseRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<CourseCard> courseCards;
    private EditText edt_search;
    private List<Task>tasks=new ArrayList<>();
    private int size=0;
    private Map<String , Integer> map =new HashMap<>();
    private DotsView dotsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_course_stagged);

        identity();
        inf();
        operation();
    }

    private  void identity(){
        edt_search = findViewById(R.id.edt_search);
        recyclerView = findViewById(R.id.rv_courses);
        dotsView=findViewById(R.id.bar);
    }
    private void operation(){
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        recyclerView.setClipToPadding(false);
        recyclerView.setHasFixedSize(true);

        courseCards = new ArrayList<>();


        setAdapter();

        adapter = new CourseRecyclerAdapter(CStagged.this, courseCards, CStagged.this,tasks);

        recyclerView.setAdapter(adapter);
        onChange_Text();
    }

    private void search(){
        String text=edt_search.getText().toString().toUpperCase();
        int x[]=new int[150],val[]=new int[150];
        for (char ch:text.toCharArray())
            x[ch]++;
        performSearch();

        for(int i=0;i<tasks.size();){
            val=new int[150];
            boolean find=true;
            for(char ch:tasks.get(i).getTitle().toUpperCase().toCharArray())
                val[ch]++;
            for(int u=0;u<150;u++){
                if(x[u]>0)
                    if(val[u]<x[u])
                        find=false;}

            if(!find){
                tasks.remove(i);
                courseCards.remove(i);}
            else
                i++;
        }

    }
    private void onChange_Text(){
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //For this example only use seach option
                    //U can use a other view with activityresult

                    setAdapter();
                   search();


                    return true;
                }
                return false;
            }
        });


    }
    //Personal Information
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }

    private void setAdapter(){
        FirebaseDatabase.getInstance().getReference().child("Task_op")
                .child(email.substring(0,email.length()-4)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    map.put(dataSnapshot.getKey(),0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tasks=new ArrayList<>();
        courseCards=new ArrayList<>();
        Query q= FirebaseDatabase.getInstance().getReference().child("Task").orderByChild("level")
                .equalTo(level).limitToFirst(100);
        if(level.charAt(0)>'9')
            q= FirebaseDatabase.getInstance().getReference().child("Task").limitToFirst(100);

        if(!Save.getType().equals("all")&&level.charAt(0)>'9')
            q=FirebaseDatabase.getInstance().getReference().child("Task")
                    .orderByChild("teacher_email").equalTo(email).limitToFirst(100);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            int x=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    for(DataSnapshot data:snapshot.getChildren()){
                    x++;
                        final Task task = data.getValue(Task.class);
                        int image=R.drawable.activities;
                        if(task.getSubject().equals("Math")){ image=R.drawable.activities4; }
                        else if(task.getSubject().equals("English")){ image=R.drawable.activities5; }
                        else if(task.getSubject().equals("Science")){ image=R.drawable.activities2; }
                        else if(task.getSubject().equals("History")){ image=R.drawable.activity_icon; }
                        else if(task.getSubject().equals("Music")){ image=R.drawable.music_icon; }
                        else if(task.getSubject().equals("Computer")){ image=R.drawable.cotact; }
                        CourseCard courseCard=new CourseCard(x,image, task.getSubject(), task.getTitle());
                        courseCard.setFile_id(data.getKey().toString());
                        if(Save.isShow()){
                            courseCards.add(courseCard);
                            tasks.add(task);
                        }
                        else if(!map.containsKey(data.getKey()) && (task.getSubject().equals(Save.getType())||Save.getType().equals("all"))){
                        courseCards.add(courseCard);
                        tasks.add(task);}

                    }

                dotsView.setVisibility(View.INVISIBLE);
                adapter = new CourseRecyclerAdapter(CStagged.this, courseCards, CStagged.this,tasks);

                recyclerView.setAdapter(adapter);
                search();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void performSearch() {
        edt_search.clearFocus();
        InputMethodManager in = (InputMethodManager) CStagged.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edt_search.getWindowToken(), 0);

    }
    @Override
    public void onDashboardCourseClick(CourseCard courseCard, ImageView imageView) {


    }

}