package com.example.anew.data;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.example.anew.Posting;
import com.example.anew.Search_Item;
import com.example.anew.notefication;

public class more_one {
    public void make_Search(Context context){

        context.startActivity(new Intent(context, Search_Item.class));
    }
    public void make_Note(Context context){

        context.startActivity(new Intent(context, notefication.class));
    }
    public void make_post(Context context ){

        context.startActivity(new Intent(context, Posting.class));
    }

}
