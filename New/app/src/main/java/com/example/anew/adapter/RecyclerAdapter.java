package com.example.anew.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anew.Massage;
import com.example.anew.News_information;
import com.example.anew.R;
import com.example.anew.data.ArrayAdapterWithIcon;
import com.example.anew.data.Server;
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

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by chintu gandhwani on 1/22/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {


    List<Server> productsList;
    List<String> id;
    Context context;
    private Bitmap[] bitmaps=new Bitmap[100];
    private boolean love[]=new boolean[100];
int scroll =0;

    public RecyclerAdapter()
    {

    }

    public RecyclerAdapter(List<Server> productsList, List<String> id, Context mContext) {
        this.productsList = productsList;
        this.id = id;
        this.context = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_bod,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        idd=position;
        save.topAnim = AnimationUtils.loadAnimation(context, R.anim.set_top);
        save.bottomAnim = AnimationUtils.loadAnimation(context, R.anim.set_left);

        final Server place=productsList.get(position);
        if(singletoon.see!=0){
            save.textView.setVisibility(View.VISIBLE);
            save.textView.setText("  "+String.valueOf(singletoon.see));
        }else save.textView.setVisibility(View.INVISIBLE);

        holder.server_love.setText(place.love);
        if(love[position]) {
            holder.server_love.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_favorite_black_24dp), null, null, null);
        }
        else{
            holder.server_love.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_favorite_border), null, null, null);
            love_found(id.get(position),singletoon.mail, holder.server_love,false,place);}

        holder.server_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                love_found(id.get(position),singletoon.mail, holder.server_love,true,place);
            }
        });
        holder.server_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singletoon.server=place;
                singletoon.id=id.get(position);
                singletoon.server_bit=bitmaps[position];
                context.startActivity(new Intent(context, Massage.class));
            }
        });


        holder.server_name.setText(place.name);
        holder.server_view.setText(place.view);
        holder.place_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("Server").child(id.get(position));
                Query query=dbr;
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("view_all").child(place.email.substring(0, place.email.length() - 4));
                final DatabaseReference finalDatabaseReference = databaseReference;
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String x=snapshot.getValue().toString();
                            finalDatabaseReference.setValue(String.valueOf(Integer.parseInt(x)+1));
                        }
                        else{
                            finalDatabaseReference.setValue("1");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()){
                            productsList.get(position).view=String.valueOf(Integer.parseInt(dataSnapshot.child("view").getValue().toString())+1);
                            productsList.get(position).love=String.valueOf(Integer.parseInt(dataSnapshot.child("love").getValue().toString()));

                            holder.server_view.setText( productsList.get(position).view);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("Server").child(id.get(position)).setValue(productsList.get(position));
                            if(!productsList.get(position).email.equals(singletoon.mail)) {
                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                databaseReference.child("note").child(place.email.substring(0, place.email.length() - 4)).child(id.get(position)).setValue(productsList.get(position));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                singletoon.server=productsList.get(position);
                singletoon.id=id.get(position);
                context.startActivity(new Intent(context, News_information.class));
            }
        });
        if(bitmaps[position]==null){
            holder.place_image.setImageBitmap(null);
            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference islandRef = storageRef.child(place.email+"/"+id.get(position));

            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new
                                                                          OnSuccessListener<byte[]>() {
                                                                              @Override
                                                                              public void onSuccess(byte[] bytes) {
                                                                                  Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                                                                  bitmaps[position]=bitmap;
                                                                                  holder.place_image.setImageBitmap(bitmap); }
                                                                          }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });}
        else holder.place_image.setImageBitmap(bitmaps[position]);

    }
    void love_found(final String id, final String email, final TextView V, final boolean B, final Server server){
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("love_server").child(email.substring(0,email.length()-4)).child(id);
        Query query=dbr;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    V.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_favorite_black_24dp),null,null,null);
                    DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("Server").child(id);
                    Query query=dbr;
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){
                                server.love=dataSnapshot.child("love").getValue().toString();
                                server.view=dataSnapshot.child("view").getValue().toString();
                                String S=(server.love);
                                int x=0+(Integer.parseInt(S));
                                server.love= String.valueOf(x);
                                productsList.get(idd).love=server.love;
                                V.setText(server.love);
                                DatabaseReference  dbR =
                                        FirebaseDatabase.getInstance().getReference();
                                dbR.child("Server").child(id).setValue(server);
                                if(!server.email.equals(singletoon.mail)) {
                                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("love_server");
                                    databaseReference.child(email.substring(0,email.length()-4)).child(id).setValue(server);
                                   }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else if(B){
                    V.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_favorite_black_24dp),null,null,null);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("love_server");
                    databaseReference.child(email.substring(0,email.length()-4)).child(id).setValue(server);
                    databaseReference= FirebaseDatabase.getInstance().getReference().child("love_all").child(server.email.substring(0,server.email.length()-4));
                    final DatabaseReference finalDatabaseReference = databaseReference;
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String x=snapshot.getValue().toString();
                                finalDatabaseReference.setValue(String.valueOf(Integer.parseInt(x)+1));
                            }
                            else{
                               finalDatabaseReference.setValue("1");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("Server").child(id);
                    Query query=dbr;
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()){
                                server.love=dataSnapshot.child("love").getValue().toString();
                                server.view=dataSnapshot.child("view").getValue().toString();
                                String S=(server.love);
                                int x=0+(Integer.parseInt(S));
                                server.love= String.valueOf(x+1);
                                productsList.get(idd).love=server.love;
                                V.setText(server.love);
                                DatabaseReference  dbR =
                                        FirebaseDatabase.getInstance().getReference();
                                dbR.child("Server").child(id).setValue(server);
                                if(!server.email.equals(singletoon.mail)) {
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    databaseReference.child("Server").child(id).setValue(server);
                                    databaseReference = FirebaseDatabase.getInstance().getReference();
                                    databaseReference.child("note").child(server.email.substring(0, server.email.length() - 4)).child(id).setValue(server);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    int idd=0;
    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
         ImageView place_image;
        TextView server_name,server_view,server_chat,open_menu, server_love;
        Button addRemoveBt;

        public MyViewHolder(View root) {
            super(root);
            server_name=root.findViewById(R.id.title_news_item);
            server_view=root.findViewById(R.id.views);
            server_chat=root.findViewById(R.id.chat);
            open_menu=root.findViewById(R.id.menu_open);
            place_image=root.findViewById(R.id.image_server);
            server_love=root.findViewById(R.id.love);}
    }
}
