package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyschool.data.Activities;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.myDbAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static java.lang.Math.min;

public class details extends AppCompatActivity {

    TextView Title ,details ,Teacher_name,rate,watch,interested;
    ImageView imageView;
    String id ="";
    Activities activities;
    int online=0;

    String rating="10.0",watching="0",interesting="0",human="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        identity();
        set_details();
        check_it();

    }

    private void check_it(){
       Query q= FirebaseDatabase.getInstance().getReference().child("A_op")
               .child(id).child("interested");
        q.orderByChild(email.substring(0,email.length()-4))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    book=true;
                }
                online++;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        q= FirebaseDatabase.getInstance().getReference().child("A_op")
                .child(id).child("rate");
        q.orderByChild(email.substring(0,email.length()-4)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    like=true;
                }
                online++;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // Item Of Page
    private void identity() {
        activities= Save.getActivities();
        imageView=findViewById(R.id.imageView8);
        Title=findViewById(R.id.title_template);
        details=findViewById(R.id.details_template);
        Teacher_name=findViewById(R.id.name_temp);
        rate=findViewById(R.id.textView3);
        watch=findViewById(R.id.textView5);
        interested=findViewById(R.id.textView6);

        inf();
        id=Save.getId();
    }



    private void set_details(){
        Title.setText(activities.getTitle());
        details.setText(activities.getDetails());
        imageView.setImageDrawable(Save.getId_photo());
        Teacher_name.setText(activities.getTeacher());
        get_data();
    }

    public void go_back(View view) {
        finish();
    }

    private void set_rate(final double x){
        FirebaseDatabase.getInstance().getReference().child("rate")
                .child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    human=(snapshot.child("human").getValue().toString());
                    rating=(snapshot.child("rate").getValue().toString());

                    int h=Integer.parseInt(human);
                    double r=Double.parseDouble(rating);
                    double sum=(h*r)/10;
                    sum+=x;
                    h++;
                    sum/=h;
                    sum*=10;
                    rating=String.valueOf(sum).substring(0,min(4,String.valueOf(sum).length()));
                    rate.setText(rating+"");
                    //rating

                    FirebaseDatabase.getInstance().getReference()
                            .child("rate").child(id).child("rate")
                            .setValue(sum);

                    FirebaseDatabase.getInstance().getReference()
                            .child("rate").child(id).child("human")
                            .setValue(h);
                }
                else{
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void get_data(){
        FirebaseDatabase.getInstance().getReference().child("rate")
                .child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    watch.setText(snapshot.child("watch").getValue().toString());
                    rate.setText(snapshot.child("rate").getValue().toString());
                    interested.setText(snapshot.child("interested").getValue().toString());
                    rating=rate.getText().toString().substring(0,min(4,rate.getText().toString().length()));
                    rate.setText(rating+"");
                    //watching Activity

                    FirebaseDatabase.getInstance().getReference()
                            .child("rate").child(id).child("watch")
                            .setValue(Integer.parseInt(watch.getText().toString())+1);
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("rate")
                            .child(id).child("watch").setValue("1");
                    FirebaseDatabase.getInstance().getReference().child("rate")
                            .child(id).child("interested").setValue("0");
                    FirebaseDatabase.getInstance().getReference().child("rate")
                            .child(id).child("rate").setValue("10.0");
                    FirebaseDatabase.getInstance().getReference().child("rate")
                            .child(id).child("human").setValue("0");
                    watch.setText("1");
                    interested.setText("0");
                    rate.setText("10.0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean like =false,book=false;

    //Personal Information
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }

    private void book_it(){
        FirebaseDatabase.getInstance().getReference().child("A_op")
                .child(id).child("interested").child(email.substring(0,email.length()-4)).setValue(email);
        book=true;
    }

    private void like_it(){
        FirebaseDatabase.getInstance().getReference().child("A_op")
                .child(id).child("rate").child(email.substring(0,email.length()-4)).setValue(email);
        like=true;
    }
    public void like(View view) {
        if(like==false&&online>=2){
        set_rate(1);
        like_it();}
    }

    public void unlike(View view) {
        if(like==false){
        set_rate(0);
        like_it();}
    }

    public void intrested(View view) {
        if(!book&&online>=2){
        book_it();
            FirebaseDatabase.getInstance().getReference().child("point")
                    .child(email.substring(0,email.length()-4))
                    .child("activity").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        int val = Integer.parseInt(snapshot.getValue().toString())+1;
                        FirebaseDatabase.getInstance().getReference().child("point")
                                .child(email.substring(0,email.length()-4))
                                .child("activity").setValue(val+"");
                    }
                    else{FirebaseDatabase.getInstance().getReference().child("point")
                            .child(email.substring(0,email.length()-4))
                            .child("activity").setValue("1");}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        FirebaseDatabase.getInstance().getReference().child("rate")
                .child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    interesting=(snapshot.child("interested").getValue().toString());
                    interested.setText(Integer.parseInt(interesting)+1+"");
                    FirebaseDatabase.getInstance().getReference().child("rate")
                            .child(id).child("interested").setValue(interested.getText().toString());
                }
                else{
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
}
