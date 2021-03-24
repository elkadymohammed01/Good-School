package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Random;

public class notefication extends AppCompatActivity {
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notefication);
        getSupportActionBar().hide();
        singletoon.see=0;
        ListView list=findViewById(R.id.list_note);
        progressBar=findViewById(R.id.progressBar);
        find(list);
    }
    void find (final ListView listView){
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("note").child(singletoon.mail.substring(0,singletoon.mail.length()-4));
        Query query=dbr;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    int x=0;
                    ArrayList<Server> places=new ArrayList<>();
                    ArrayList<String> id=new ArrayList<>();

                    try {for(DataSnapshot dd:dataSnapshot.getChildren()){

                        Server place=dataSnapshot.getValue( Server.class);
                        place.name=dd.child("name").getValue().toString();
                        place.information=dd.child("information").getValue().toString();
                        place.email=dd.child("email").getValue().toString();
                        place.love=dd.child("love").getValue().toString();
                        place.view=dd.child("view").getValue().toString();
                        places.add(place);
                        id.add(dd.getKey().toString());

                    }
                    }

                    catch(Exception ex){
                        Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                    }
                    listView.setAdapter(new Adapt(getApplicationContext(),places.size(),places,id));
                    progressBar.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
class Adapt extends BaseAdapter {
    ArrayList<Server> collection;
    int size;
    Random rand = new Random(); //instance of random class
    int upperbound = 1010;
    ArrayList<String> id = new ArrayList<>();
    //generate random values from 0-24
    int int_random = rand.nextInt(upperbound);
    Context context;
    Bitmap[] bitmaps;

    Adapt(Context context, int size, ArrayList<Server> collection, ArrayList<String> id) {
        this.context = context;
        this.size = size;
        this.collection = collection;
        bitmaps = new Bitmap[size];
        this.id = id;
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Server place = collection.get((position));
        LayoutInflater inflater = LayoutInflater.from(context);

        convertView = inflater.inflate(R.layout.note, null);
        View root = convertView;
        TextView server_name = root.findViewById(R.id.note_title);
        TextView server_info = root.findViewById(R.id.note_sender);
        TextView server_love = root.findViewById(R.id.note_love);
        TextView server_comment = root.findViewById(R.id.note_comment);
        final ImageView place_image = root.findViewById(R.id.note_photo);

        server_name.setText(place.name);
        if(Integer.parseInt(place.view)>0)
        server_info.setText(place.view+" " +" person see this report tell now");
        else server_info.setVisibility(View.INVISIBLE);
        if(Integer.parseInt(place.love)>0)
        server_love.setText(place.love+" " +" person love  this report tell now");
        else server_love.setVisibility(View.INVISIBLE);
        if(false)
        server_comment.setText(place.view+" " +" person see comment on this report tell now");
        else server_comment.setVisibility(View.INVISIBLE);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singletoon.server=place;
                singletoon.server_bit=bitmaps[position];
context.startActivity(new Intent(context,News_information.class));
                DatabaseReference database= FirebaseDatabase.getInstance().getReference()
                        .child("note").child(singletoon.mail.substring(0,singletoon.mail.length()-4)).child(id.get(position));
                database.removeValue();
                    }
        });
        if (bitmaps[position] == null) {

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            final ProgressDialog progressDialog
                    = new ProgressDialog(context);
            StorageReference islandRef = storageRef.child(place.email + "/" + id.get(position));

            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new
                                                                          OnSuccessListener<byte[]>() {
                                                                              @Override
                                                                              public void onSuccess(byte[] bytes) {
                                                                                  Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                                                                  bitmaps[position] = bitmap;
                                                                                  place_image.setImageBitmap(bitmap);
                                                                              }
                                                                          }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } else place_image.setImageBitmap(bitmaps[position]);

        return convertView;
    }

}