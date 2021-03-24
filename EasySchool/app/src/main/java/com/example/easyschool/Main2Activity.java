package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easyschool.adapter.ActivityRecyclerAdapter;
import com.example.easyschool.data.Activities;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.model.CStagged;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        put_ids();
        inf();
        identity();
        set_details();
        show_lessons();

    }
    ArrayList<Integer>pos=new ArrayList<>();ArrayList<LinearLayout>Line=new ArrayList<>();ArrayList<String>word=new ArrayList<>();
        private BottomNavigationView bottomNavigationView;
        private TextView user_name ,all;
        private RecyclerView list_activty;
        private void put_ids(){
            pos.add(R.id.science);pos.add(R.id.history);pos.add(R.id.english);pos.add(R.id.math);pos.add(R.id.ar);pos.add(R.id.music);pos.add(R.id.computer);
            word.add("Science");word.add("History");word.add("English");word.add("Math");word.add("Arabic");word.add("Music");word.add("Computer");
        }
        // Item Of Page
        private void identity() {
            all=((TextView)findViewById(R.id.all_act));
            Save.setShow(false);
            for(int i=0;i<pos.size();i++)
                Line.add((LinearLayout) findViewById(pos.get(i)));
            list_activty=findViewById(R.id.list_activity);
            user_name =findViewById(R.id.name_user);}
        private void show_lessons(){
            for(int i=0;i<pos.size();i++) {
                final int finalI = i;
                Line.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Save.setType(word.get(finalI));
                        Intent intentGetStarted = new Intent(Main2Activity.this, CStagged.class);
                        startActivity(intentGetStarted);
                    }
                });
            }

        }

            //Details of Page
        private void set_details(){
            user_name.setText("Hello "+name.split(" ")[0]+"!");
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            list_activty.setLayoutManager(layoutManager);
            find_Activity(list_activty);
        }

    // find my Activity at resently activity
    void find_Activity( final RecyclerView listView){


        final ArrayList<Activities> Tasks =new ArrayList<>();
        final ArrayList<String> id=new ArrayList<>();
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Activity_value.class));
            }
        });
        Query dbr = FirebaseDatabase.getInstance().getReference().child("Activity").limitToFirst(10);
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
                        Tasks.add(task);
                }
                listView.setAdapter(new ActivityRecyclerAdapter(Tasks,getApplicationContext(),id));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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

        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intentGetStarted;
                switch (item.getItemId()) {
                    case R.id.navigationMyProfile:
                        intentGetStarted = new Intent(Main2Activity.this,Profile.class);
                        startActivity(intentGetStarted);
                        break;
                    case R.id.navigationMyCourses:
                        intentGetStarted = new Intent(Main2Activity.this,nop.class);
                        startActivity(intentGetStarted);
                        break;
                    case R.id.navigationHome:
                        break;
                    case R.id.navigationSearch:
                        intentGetStarted = new Intent(Main2Activity.this,question_st.class);
                        startActivity(intentGetStarted);
                        break;
                    case R.id.navigationMenu:
                        intentGetStarted = new Intent(Main2Activity.this,setting.class);
                        startActivity(intentGetStarted);
                        break;
                }
//            return false;
                return true;
            }
        };


        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }



        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    public void show(View view) {
        Save.setType("all");
        Intent intentGetStarted = new Intent(Main2Activity.this, CStagged.class);
        startActivity(intentGetStarted);
    }
}