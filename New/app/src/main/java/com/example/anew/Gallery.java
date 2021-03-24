package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anew.adapter.RecyclerAdapter;
import com.example.anew.data.Server;
import com.example.anew.data.save;
import com.example.anew.data.singletoon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().hide();
        progressBar=findViewById(R.id.progressBar2);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        final RecyclerView lnews=findViewById(R.id.list_me);
        lnews.setLayoutManager(mLayoutManager);
        find(lnews);
    }
    void find (final RecyclerView listView){


        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("Server");
        Query query=dbr.orderByChild("email").equalTo(singletoon.mail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    int x=0;
                    ArrayList<Server> places=new ArrayList<>();ArrayList<String> id=new ArrayList<>();

                    for(DataSnapshot dd:dataSnapshot.getChildren()){

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



                    listView.setAdapter(new RecyclerAdapter(places,id,getApplicationContext()));
                    progressBar.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    ProgressBar progressBar;
}
