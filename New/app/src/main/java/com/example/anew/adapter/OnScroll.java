package com.example.anew.adapter;


import android.view.View;

import com.example.anew.data.save;

public class OnScroll implements View.OnScrollChangeListener {
    @Override
    public void onScrollChange(View v, int X, int Y, int oldX, int oldY) {

    if(X>=oldX&&Y>=oldY){lower(X,Y);}
    else{Up(X,Y);}
    }
    public void Up(int new_X,int new_Y){
        save.line.setVisibility(View.VISIBLE);
    }
    public void lower(int new_X,int new_Y){
        save.line.setVisibility(View.INVISIBLE);
    }

}
