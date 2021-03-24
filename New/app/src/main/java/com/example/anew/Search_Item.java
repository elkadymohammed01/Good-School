package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anew.adapter.RecyclerAdapter;
import com.example.anew.data.Server;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Search_Item extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__item);
        list=findViewById(R.id.rv_courses);
        getSupportActionBar().hide();
        ((EditText)findViewById(R.id.edt_search)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                           try{ onSearchAction(v);}
                           catch (Exception ex){Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();}
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

    }
RecyclerView list;
    int a[]=new int[131];
    private void onSearchAction(TextView v) {
        String volo = v.getText().toString().toLowerCase();
        final ArrayList<String> id=new ArrayList<>();
        final ArrayList <Server>Servers=new ArrayList<>();
        int x=7;
        Map<String,String> mop=new HashMap<>();
        for(String S:volo.toLowerCase().split(" ")){mop.put(S,S);}
        for(String S:mop.keySet()){
                FirebaseDatabase.getInstance().getReference().child("Search").child(S)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot sdata:snapshot.getChildren()){
                               if(!map.containsKey(sdata.getKey())){
                                Server place=sdata.getValue( Server.class);
                                place.name=sdata.child("name").getValue().toString();
                                place.information=sdata.child("information").getValue().toString();
                                place.email=sdata.child("email").getValue().toString();
                                place.view=sdata.child("view").getValue().toString();
                                place.love=sdata.child("love").getValue().toString();
                                place.type=sdata.child("type").getValue().toString();
                                place.user=sdata.child("user").getValue().toString();
                               node node=new node();
                               node.id=sdata.getKey();
                               node.server=place;
                               nodes.add(node);}
                               if(map.containsKey(sdata.getKey())){map.put(sdata.getKey(),map.get(id)+1);}
                               else map.put(sdata.getKey(),1);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            x--;
            if(x<=0)break;}
            Toast.makeText(getApplicationContext(),nodes.size()+" ",Toast.LENGTH_LONG).show();

        Collections.sort(nodes);
for(node node:nodes){
    id.add(node.id);
    Servers.add(node.server);
}

list.setAdapter(new RecyclerAdapter(Servers,id,getApplicationContext()));
//recyclerView Create 2 iteam in One
        GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        list.setLayoutManager(mLayoutManager);
        nodes=new ArrayList<>();
        map=new HashMap<>();
    }
    static ArrayList<node>nodes=new ArrayList<>();
    static Map<String,Integer>map=new HashMap<>();
}
class node implements Comparable<node>{
    Server server;
    String id;
    @Override
    public int compareTo(node o) {
        if(Search_Item.map.containsKey(id))
        return Search_Item.map.get(id);
        else return -1;
    }
}