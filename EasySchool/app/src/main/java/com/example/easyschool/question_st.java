package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easyschool.adapter.CoursesItemClickListener;
import com.example.easyschool.adapter.lessonAdapter;
import com.example.easyschool.adapter.question_adapter;
import com.example.easyschool.data.Question;
import com.example.easyschool.data.lesson;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.matchcourses.Item;
import com.example.easyschool.matchcourses.ShopAdapter;
import com.example.easyschool.model.CourseCard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class question_st  extends AppCompatActivity
        implements DiscreteScrollView.OnItemChangedListener<ShopAdapter.ViewHolder>,
        View.OnClickListener , CoursesItemClickListener {
    private List<Item> data= new ArrayList();

    private DiscreteScrollView itemPicker;
    private InfiniteScrollAdapter<?> infiniteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_question_st);

        data=  Arrays.asList(
                new Item(3, "Math", "98 Question available", R.drawable.education_5),
                new Item(4, "Science", "78 Question available", R.drawable.education_3),
                new Item(5, "History", "106 Question available", R.drawable.education_2),
                new Item(1, "Arabic", "102 Question available", R.drawable.education),
                new Item(2, "English", "30 Question available", R.drawable.education_1),
                new Item(6, "Music", "45 Question available", R.drawable.music_icon),
                new Item(7, "Computer", "85 Question available", R.drawable.education_4)

        );
        itemPicker = findViewById(R.id.item_picker);
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
        itemPicker.addOnItemChangedListener(this);
        infiniteAdapter = InfiniteScrollAdapter.wrap(new ShopAdapter(data));
        itemPicker.setAdapter(infiniteAdapter);
        itemPicker.setItemTransitionTimeMillis(500);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        recyclerView = findViewById(R.id.list_lesson);

        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        );
        recyclerView.setClipToPadding(false);
        recyclerView.setHasFixedSize(true);
        inf();

    }

    //Personal Information
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }

    private question_adapter adapter;
    private RecyclerView recyclerView;
    private List<Question> courseCards;
    private List<String>ids;
    private Map<String,Integer> map;
    private void setAdapter(String type){
        map=new HashMap<>();
        FirebaseDatabase.getInstance().getReference()
                .child("Qid")
                .child(email.substring(0,email.length()-4)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dd:snapshot.getChildren())
                    map.put(dd.getKey(),0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Query q= FirebaseDatabase.getInstance().getReference().child("Questions").orderByChild("subject").equalTo(type);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            int x=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    for(DataSnapshot data:snapshot.getChildren()){
                        final String  id =data.getKey();
                        final Question question = data.getValue(Question.class);
                        if(!map.containsKey(id)) {
                            ids.add(id);
                            courseCards.add(question);
                        }
                    }

                adapter = new question_adapter( courseCards , getApplicationContext(),ids);
                    adapter.activity=question_st.this;
                recyclerView.setAdapter(adapter);
                recyclerView.swapAdapter(adapter,false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDashboardCourseClick(CourseCard courseCard, ImageView imageView) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable ShopAdapter.ViewHolder viewHolder, int adapterPosition) {
        courseCards = new ArrayList<>();
        ids=new ArrayList<>();
        setAdapter(viewHolder.tv_titulo.getText().toString());
    }
}
