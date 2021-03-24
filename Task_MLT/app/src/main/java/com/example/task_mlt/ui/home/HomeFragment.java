package com.example.task_mlt.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_mlt.R;
import com.example.task_mlt.adapter.RecyclerAdapter;
import com.example.task_mlt.data.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView lnews=root.findViewById(R.id.list);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        lnews.setLayoutManager(mLayoutManager);
        get_events(lnews);
        return root;
    }
    void get_events(final RecyclerView list){
        final ArrayList<Event> events =new ArrayList<>();
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().child("Event");
        Query query = dbr;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dd:snapshot.getChildren()){
                    Event event =new Event();
                    event.name=dd.child("name").getValue().toString();

                    event.email=dd.child("email").getValue().toString();
                    event.information=dd.child("information").getValue().toString();
                    event.manger=dd.child("manger").getValue().toString();
                    event.time=dd.child("time").getValue().toString();
                    event.id=dd.child("id").getValue().toString();
                    events.add(event);
                }
                list.setAdapter((new RecyclerAdapter(events,getContext())));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}