package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.easyschool.adapter.CourseRecyclerAdapter;
import com.example.easyschool.adapter.CoursesItemClickListener;
import com.example.easyschool.adapter.lessonAdapter;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.lesson;
import com.example.easyschool.matchcourses.Item;
import com.example.easyschool.matchcourses.Shop;
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
import java.util.List;

public class nop extends AppCompatActivity
        implements DiscreteScrollView.OnItemChangedListener<ShopAdapter.ViewHolder>,
        View.OnClickListener , CoursesItemClickListener {

    private List<Item> data= new ArrayList();

    private DiscreteScrollView itemPicker;
    private InfiniteScrollAdapter<?> infiniteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nop);

        data=  Arrays.asList(
                new Item(3, "Math", "65 lesson available", R.drawable.education_5),
                new Item(4, "Science", "18 lesson available", R.drawable.education_3),
                new Item(5, "History", "36 lesson available", R.drawable.education_2),
                new Item(1, "Arabic", "12 lesson available", R.drawable.education),
                new Item(2, "English", "50 lesson available", R.drawable.education_1),
                new Item(6, "Music", "15 lesson available", R.drawable.music_icon),
                new Item(7, "Computer", "45 lesson available", R.drawable.education_4)

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


        courseCards=new ArrayList<>();
        adapter = new lessonAdapter(getApplicationContext(), courseCards);
        recyclerView.setAdapter(adapter);
    }

    private lessonAdapter adapter;
    private RecyclerView recyclerView;
    private List<CourseCard> courseCards;
    private void setAdapter(String type){
        Query q= FirebaseDatabase.getInstance().getReference().child("Lesson")
                .orderByChild("subject").equalTo(type).limitToLast(100);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            int x=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    for(DataSnapshot data:snapshot.getChildren()){
                        x++;
                        lesson l=data.getValue(lesson.class);

                        int image=R.drawable.file_icon;
                        if(l.getType().equals("image")){ image=R.drawable.image_icon; }
                        else if(l.getType().equals("details")){ image=R.drawable.details_icon; }
                        else if(l.getType().equals("video")){ image=R.drawable.video_icon; }
                        courseCards.add(new CourseCard(x,image, l.getSubject(), l.getTitle()));
                        courseCards.get(courseCards.size()-1).setFile_type(l.getType());
                        courseCards.get(courseCards.size()-1).setFile_id(l.getFile());
                    }

                adapter = new lessonAdapter(getApplicationContext(), courseCards);
                recyclerView.swapAdapter(adapter,false);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onClick(View view) {


    }

    @Override
    public void onCurrentItemChanged(@Nullable ShopAdapter.ViewHolder viewHolder, int adapterPosition) {
        courseCards = new ArrayList<>();

        setAdapter(viewHolder.tv_titulo.getText().toString());
    }

    @Override
    public void onDashboardCourseClick(CourseCard courseCard, ImageView imageView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
