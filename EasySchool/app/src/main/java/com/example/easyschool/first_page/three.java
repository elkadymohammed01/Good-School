package com.example.easyschool.first_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.easyschool.Main2Activity;
import com.example.easyschool.R;
import com.example.easyschool.login_page;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class three extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_three, container, false);

        return v;
    }
}
