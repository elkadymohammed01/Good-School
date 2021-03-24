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

import com.example.easyschool.adapter.CourseRecyclerAdapter;
import com.example.easyschool.adapter.CoursesItemClickListener;
import com.example.easyschool.adapter.PersonRecyclerAdapter;
import com.example.easyschool.data.DotsView;
import com.example.easyschool.data.Question;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.User;
import com.example.easyschool.data.lesson;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.model.CStagged;
import com.example.easyschool.model.CourseCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class person extends AppCompatActivity
        implements CoursesItemClickListener {
    private PersonRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<CourseCard> courseCards;
    private List<User>users =new ArrayList<>();
    private EditText edt_search;
    private DotsView dotsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_person);


        edt_search = findViewById(R.id.edt_search);
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    //For this example only use seach option
                    //U can use a other view with activityresult
                    performSearch();
                    setAdapter();

                    search();

                    return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.rv_courses);

        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        recyclerView.setClipToPadding(false);
        recyclerView.setHasFixedSize(true);
        dotsView=findViewById(R.id.bar);

        inf();

        courseCards = new ArrayList<>();
        setAdapter();


        adapter = new PersonRecyclerAdapter(this, courseCards,
                this,users);

        recyclerView.setAdapter(adapter);

    }

    private void search(){
        String text=edt_search.getText().toString().toUpperCase();
        int x[]=new int[150],val[]=new int[150];
        for (char ch:text.toCharArray())
            x[ch]++;
        performSearch();

        for(int i=0;i<users.size();){
            val=new int[150];
            boolean find=true;
            for(char ch:users.get(i).getName().toUpperCase().toCharArray())
                val[ch]++;
            for(int u=0;u<150;u++){
                if(x[u]>0)
                    if(val[u]<x[u])
                        find=false;}

            if(!find){
                users.remove(i);
                courseCards.remove(i);}
            else
                i++;
        }
        adapter = new PersonRecyclerAdapter(this, courseCards,
                this,users);

        recyclerView.setAdapter(adapter);

    }
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }
    private void setAdapter_user(){
        users =new ArrayList<>();
        Query q= FirebaseDatabase.getInstance().getReference().child("Following").child(email.substring(0,email.length()-4));
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            int x=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {

                        final User user = data.getValue(User.class);
                        int image = R.drawable.activities;
                        if (x % 4 == 1) {
                            image = R.drawable.activities2;
                        } else if (x % 4 == 2) {
                            image = R.drawable.details;
                        } else if (x % 4 == 3) {
                            image = R.drawable.video;
                        }
                        x++;
                        courseCards.add(new CourseCard(x, image, user.getName(), "Level " + user.getLevel()));
                        users.add(user);
                    }

                    dotsView.setVisibility(View.INVISIBLE);
                }
                    search();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void task_sent(){
        Query q= FirebaseDatabase.getInstance().getReference().child("Sent_Task")
                .child(email.substring(0,email.length()-4)).child(Save.getType());
        q.addListenerForSingleValueEvent(new ValueEventListener() {

            int x=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    for (final DataSnapshot data : snapshot.getChildren()) {
                        String id = data.getKey();
                        Query q = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    User user = new User();
                                    user.setPhone(snapshot.child("phone").getValue().toString());
                                    user.setName(snapshot.child("name").getValue().toString());
                                    user.setEmail(snapshot.child("email").getValue().toString());
                                    user.setLevel(snapshot.child("level").getValue().toString());
                                    int image = R.drawable.activities;
                                    if (x % 4 == 1) {
                                        image = R.drawable.activities2;
                                    } else if (x % 4 == 2) {
                                        image = R.drawable.details;
                                    } else if (x % 4 == 3) {
                                        image = R.drawable.video;
                                    }
                                    x++;
                                    courseCards.add(new CourseCard(x, image, user.getName(), "Subject " + user.getLevel()));
                                    users.add(user);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    dotsView.setVisibility(View.INVISIBLE);
                }
                    search();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setAdapter(){

        dotsView.setVisibility(View.VISIBLE);
        users=new ArrayList<>();
        courseCards=new ArrayList<>();
        users =new ArrayList<>();
        if(Save.getType().equals("2")){
            setAdapter_user();
            return;
        }
        else if (Save.getType().length()>2){
            task_sent();
            return;
        }
        Query q= FirebaseDatabase.getInstance().getReference().child("Users");
       q.addListenerForSingleValueEvent(new ValueEventListener() {
            int x=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot data : snapshot.getChildren()) {


                        final User user = data.getValue(User.class);
                        int image = R.drawable.activities;
                        if (x % 4 == 1) {
                            image = R.drawable.activities2;
                        } else if (x % 4 == 2) {
                            image = R.drawable.details;
                        } else if (x % 4 == 3) {
                            image = R.drawable.video;
                        }
                        if (user.getLevel().charAt(0) <= '9' && Save.getType().equals("0")) {
                            x++;
                            courseCards.add(new CourseCard(x, image, user.getName(), "Level " + user.getLevel()));
                            users.add(user);
                        } else if (user.getLevel().charAt(0) > '9' && Save.getType().equals("1")) {
                            x++;
                            courseCards.add(new CourseCard(x, image, user.getName(), "Subject " + user.getLevel()));
                            users.add(user);
                        }
                    }

                    dotsView.setVisibility(View.INVISIBLE);
                }
                    search();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void performSearch() {
        edt_search.clearFocus();
        InputMethodManager in = (InputMethodManager) person.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(edt_search.getWindowToken(), 0);

    }
    @Override
    public void onDashboardCourseClick(CourseCard courseCard, ImageView imageView) {


    }

}