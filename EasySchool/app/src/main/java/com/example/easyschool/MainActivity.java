package com.example.easyschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.easyschool.adapter.viewpager;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.first_page.one;
import com.example.easyschool.first_page.three;
import com.example.easyschool.first_page.two;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView[]images=new ImageView[3];
    LottieAnimationView lottie;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        translation();
        page_view();

    }
    void page_view(){
viewPager = findViewById(R.id.pager);
        viewpager adapter = new viewpager(getSupportFragmentManager());
        adapter.addFragment(new one(), "ONE");
        adapter.addFragment(new two(), "TWO");
        adapter.addFragment(new three(), "Three");
        viewPager.setAdapter(adapter);

        Save.setContext(getApplicationContext());

        final myDbAdapter myDb =new myDbAdapter(getApplicationContext());

        FloatingActionButton ft =findViewById(R.id.st);
        ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(myDb.getData().length()<9){
                startActivity(new Intent(getApplicationContext(), login_page.class));}
                else
                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                finish();
            }
        });
    }

    void translation(){
        images[0]=findViewById(R.id.imageView);
        images[1]=findViewById(R.id.image_two);
        textView=findViewById(R.id.title);
        lottie=findViewById(R.id.lottieAnimationView);
        textView.animate().setStartDelay(4000).setDuration(2000).translationY(2600);
        lottie.animate().setStartDelay(4000).setDuration(2000).translationY(2600);
        images[0].animate().setStartDelay(4000).setDuration(1500).translationY(-2600);
        images[1].animate().setStartDelay(4000).setDuration(1500).translationY(-2600);
    }
}
