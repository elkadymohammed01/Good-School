package com.example.easyschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.easyschool.adapter.viewpager;
import com.example.easyschool.first_page.Sign_Up;
import com.example.easyschool.first_page.log_in;
import com.example.easyschool.first_page.one;
import com.example.easyschool.first_page.three;
import com.example.easyschool.first_page.two;

public class login_page extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        page_view();
    }

    void page_view(){
        viewPager = findViewById(R.id.pager);
        viewpager adapter = new viewpager(getSupportFragmentManager());
        adapter.addFragment(new log_in(), "ONE");
        adapter.addFragment(new Sign_Up(), "two");
        viewPager.setAdapter(adapter);
    }


}
