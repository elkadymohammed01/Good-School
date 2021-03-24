package com.example.task_mlt.adapter;


import android.view.View;


public class OnScroll implements View.OnScrollChangeListener {
    @Override
    public void onScrollChange(View v, int X, int Y, int oldX, int oldY) {

    if(X>=oldX&&Y>=oldY){lower(X,Y);}
    else{Up(X,Y);}
    }
    public void Up(int new_X,int new_Y){ }
    public void lower(int new_X,int new_Y){
    }

}
