package com.example.easyschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.easyschool.data.Save;
import com.example.easyschool.data.myDbAdapter;

import java.util.ArrayList;

public class quesion_type extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quesion_type);
        Save.setType("0");
        put_ids();
        inf();
        identity();
        do_it();
    }

    ArrayList<Integer> line_id=new ArrayList<>(),image_id=new ArrayList<>();
    ArrayList<ImageView>imageViews=new ArrayList<>();
    ArrayList<LinearLayout>linearLayouts=new ArrayList<>();
    private void put_ids(){
        line_id.add(R.id.line1);line_id.add(R.id.line2);line_id.add(R.id.line3);line_id.add(R.id.line4);line_id.add(R.id.line5);
        image_id.add(R.id.image1);image_id.add(R.id.image2);image_id.add(R.id.image3);image_id.add(R.id.image4);image_id.add(R.id.image5);
    }

    //Personal Information
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }
    // Item Of Page
    private void identity() {
        for(int i=0;i<5;i++) {
            imageViews.add(((ImageView)findViewById(image_id.get(i))));
            linearLayouts.add(((LinearLayout)findViewById(line_id.get(i))));
        }
    }

    private void do_it(){
    for(int i=0;i<5;i++){
        final int finalI = i;
        linearLayouts.get(i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int u=0;u<5;u++)
                    imageViews.get(u).setImageResource(R.color.grey);
                imageViews.get(finalI).setImageResource(R.drawable.correct_answer);
                Save.setType(finalI+"");
            }
        });
    }

    }

    public void add_question(View view) {
        startActivity(new Intent(getApplicationContext(),Question_details.class));
    }
}
