package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyschool.adapter.ActivityRecyclerAdapter;
import com.example.easyschool.adapter.CourseRecyclerAdapter;
import com.example.easyschool.adapter.CoursesItemClickListener;
import com.example.easyschool.data.Activities;
import com.example.easyschool.data.DotsView;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.model.CourseCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Activity_value extends AppCompatActivity
        implements CoursesItemClickListener {
    private CourseRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<CourseCard> courseCards;
    private EditText edt_search;
    private DotsView dotsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_value);


        edt_search = findViewById(R.id.edt_search);
        dotsView=findViewById(R.id.bar);
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //For this example only use seach option
                    //U can use a other view with activityresult
                    search();
                    performSearch();

                    Toast.makeText(Activity_value.this,
                            " Searching : " + edt_search.getText().toString().trim(),
                            Toast.LENGTH_SHORT).show();

                    return true;
                }
                return false;
            }
        });

        inf();
        recyclerView = findViewById(R.id.rv_courses);

        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        recyclerView.setClipToPadding(false);
        recyclerView.setHasFixedSize(true);

        courseCards = new ArrayList<>();
        setAdapter();

    }
    private void search(){
        String text=edt_search.getText().toString().toUpperCase();
        int x[]=new int[150],val[]=new int[150];
        for (char ch:text.toCharArray())
            x[ch]++;
        performSearch();

        for(int i=0;i<activities.size();){
            val=new int[150];
            boolean find=true;
            for(char ch:activities.get(i).getTitle().toUpperCase().toCharArray())
                if(ch<145&&ch>=0)
                val[ch]++;
            for(int u=0;u<150;u++){
                if(x[u]>0)
                    if(val[u]<x[u])
                        find=false;}

            if(!find){
                activities.remove(i);
                courseCards.remove(i);}
            else
                i++;
        }

    }
    //Personal Information
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }

    final ArrayList<Activities> activities =new ArrayList<>();
    private void setAdapter(){
        final ArrayList<String> id=new ArrayList<>();
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Activity");
        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dd:snapshot.getChildren()){
                    Activities task =new Activities();
                    task.setTime(dd.child("time").getValue().toString());
                    task.setTeacher(dd.child("teacher").getValue().toString());
                    task.setTeacher_email(dd.child("teacher_email").getValue().toString());
                    task.setLevel(dd.child("level").getValue().toString());
                    task.setTitle(dd.child("title").getValue().toString());
                    task.setDetails(dd.child("details").getValue().toString());
                    id.add(dd.getKey());
                    activities.add(task);
                }

                dotsView.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(new ActivityRecyclerAdapter(activities,getApplicationContext(),id));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void performSearch() {
        edt_search.clearFocus();
        InputMethodManager in = (InputMethodManager) Activity_value.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edt_search.getWindowToken(), 0);

    }
    @Override
    public void onDashboardCourseClick(CourseCard courseCard, ImageView imageView) {


    }

}