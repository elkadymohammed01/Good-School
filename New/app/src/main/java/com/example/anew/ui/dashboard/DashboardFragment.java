package com.example.anew.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anew.Posting;
import com.example.anew.R;
import com.example.anew.adapter.RecyclerAdapter;
import com.example.anew.data.Server;
import com.example.anew.data.save;
import com.example.anew.data.singletoon;
import com.example.anew.notefication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
progressBar=root.findViewById(R.id.progressBar3);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        final RecyclerView lnews=root.findViewById(R.id.list_love);
        lnews.setLayoutManager(mLayoutManager);
        find(lnews);
        return root;
    }
    void find (final RecyclerView listView){


        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("love_server").child(singletoon.mail.substring(0,singletoon.mail.length()-4));
        Query query=dbr;

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
