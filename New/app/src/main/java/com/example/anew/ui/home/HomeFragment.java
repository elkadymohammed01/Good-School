package com.example.anew.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anew.Massage;
import com.example.anew.News_information;
import com.example.anew.R;
import com.example.anew.adapter.OnScroll;
import com.example.anew.adapter.RecyclerAdapter;
import com.example.anew.data.ArrayAdapterWithIcon;
import com.example.anew.data.Server;
import com.example.anew.data.more_one;
import com.example.anew.data.myDbAdapter;
import com.example.anew.data.save;
import com.example.anew.data.singletoon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HomeFragment extends Fragment {
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final more_one more=new more_one();
        save.line=root.findViewById(R.id.main_action);
        LinearLayout line=root.findViewById(R.id.write_post);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more.make_post(getContext());
            }
        });
        LinearLayout lol=root.findViewById(R.id.note);
        lol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more.make_Note(getContext());
            }
        });
        LinearLayout lok=root.findViewById(R.id.search_icon);
        lok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more.make_Search(getContext());
            }
        });
        save.textView=root.findViewById(R.id.number_notify);
        save.textView.setVisibility(singletoon.see==0?View.INVISIBLE:View.VISIBLE);
        progressBar=root.findViewById(R.id.main_bar);
        if(singletoon.see!=0){
            save.textView.setVisibility(View.VISIBLE);
            save.textView.setText(String.valueOf(singletoon.see));
        }
        final RecyclerView lnews=root.findViewById(R.id.list_news);
        //recyclerView Create 2 iteam in One
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        lnews.setLayoutManager(mLayoutManager);
        save.recyclerView=root.findViewById(R.id.list_news);
        find(lnews);
        lnews.setOnScrollChangeListener(new OnScroll());
        //close create
      final int id[]={R.id.anime,R.id.manga,R.id.manho};
        final String []S= {"Food","Drinks","Desert"};
        final TextView textViews[]=new TextView[3];
        for(int i=0;i<3;i++)textViews[i]=root.findViewById(id[i]);
        for(int i=0;i<3;i++){
            final int finalI = i;
            textViews[i].setBackground(root.getResources().getDrawable(R.drawable.black_border));
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    find(lnews,S[finalI]);
                    textViews[finalI].setBackground(root.getResources().getDrawable(R.drawable.good_bord));
                    textViews[(finalI+1)%3].setBackground(root.getResources().getDrawable(R.drawable.black_border));
                    textViews[(finalI+2)%3].setBackground(root.getResources().getDrawable(R.drawable.black_border));
                }
            });
        }
        myDbAdapter myone=new myDbAdapter(getContext());
        try{
            String []col=myone.getData_inf();
            singletoon.mail=col[1];
            singletoon.name=col[0];
            singletoon.phone=col[2];}
        catch (Exception ex){
            Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_LONG).show();
        }

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=root.findViewById(R.id.number_notify);
                textView.setVisibility(singletoon.see==0?View.INVISIBLE:View.VISIBLE);
                if(singletoon.see!=0){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(" "+String.valueOf(singletoon.see));
                }
            }
        });
        return root;
    }
    void find (final RecyclerView listView){


        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("Server");
        Query query=dbr;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    int x=0;
                    ArrayList<Server> places=new ArrayList<>();ArrayList<String> id=new ArrayList<>();

                    try {for(DataSnapshot dd:dataSnapshot.getChildren()){

                        Server place=dataSnapshot.getValue( Server.class);
                        place.name=dd.child("name").getValue().toString();
                        place.information=dd.child("information").getValue().toString();
                        place.email=dd.child("email").getValue().toString();
                        place.view=dd.child("view").getValue().toString();
                        place.love=dd.child("love").getValue().toString();
                        place.type=dd.child("type").getValue().toString();
                        place.user=dd.child("user").getValue().toString();
                        places.add(place);
                        x++;
                        id.add(dd.getKey().toString());
                     }
                    }

                    catch(Exception ex){
                        Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_LONG).show();
                    }
                    listView.setAdapter(new RecyclerAdapter(places,id,getContext()));
                    progressBar.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    void find (final RecyclerView listView,String type){


        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("Server");
        Query query=dbr.orderByChild("type").equalTo(type);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    int x=0;
                    ArrayList<Server> places=new ArrayList<>();ArrayList<String> id=new ArrayList<>();

                    try {for(DataSnapshot dd:dataSnapshot.getChildren()){

                        Server place=dataSnapshot.getValue( Server.class);
                        place.name=dd.child("name").getValue().toString();
                        place.information=dd.child("information").getValue().toString();
                        place.email=dd.child("email").getValue().toString();

                        place.view=dd.child("view").getValue().toString();
                        place.love=dd.child("love").getValue().toString();
                        place.type=dd.child("type").getValue().toString();
                        place.user=dd.child("user").getValue().toString();

                        places.add(place);
                        x++;
                        id.add(dd.getKey().toString());

                    }
                    }

                    catch(Exception ex){
                        Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_LONG).show();
                    }
                    listView.setAdapter(new RecyclerAdapter(places,id,getContext()));
                    progressBar.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
