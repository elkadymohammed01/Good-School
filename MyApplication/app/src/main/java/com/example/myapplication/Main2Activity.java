package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.adapter.viewpager;
import com.example.myapplication.first_page.one;
import com.example.myapplication.first_page.three;
import com.example.myapplication.first_page.two;

import id.indosw.liquidswipe_lib.LiquidPager;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ViewPager pager = findViewById(R.id.pager);
        viewpager viewpager =new viewpager(getSupportFragmentManager(),1);
        viewpager.addFragment(new one(),"one");
        viewpager.addFragment(new two(),"two");
        viewpager.addFragment(new three(),"three");
        pager.setAdapter(viewpager);

    }
}
