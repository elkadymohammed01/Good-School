package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.easyschool.data.Check_Text;
import com.example.easyschool.data.Question;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.data.op_save;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Question_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_details);
        inf();
        put_ids();
        identity();
        hide_show();

    }

    ArrayList<Integer> ed_id=new ArrayList<>();
    ArrayList<EditText>editTexts=new ArrayList<>();
    EditText question;
    ImageView file;
    EditText lv;
    String ans[]=new String[5];
        //put id
        private void put_ids(){
            ed_id.add(R.id.choose1);ed_id.add(R.id.choose2);ed_id.add(R.id.choose3);ed_id.add(R.id.choose4);ed_id.add(R.id.good_answer);
            ans[0]="null";ans[1]="null";ans[2]="null";ans[3]="null";ans[4]="1";
        }

        // Information
        private String name,phone,email,level;
        private void inf(){
            myDbAdapter mydb = new myDbAdapter(getApplicationContext());
            String all[]=mydb.getData_inf();
            name=all[0];email=all[1];phone=all[2];level=all[3];
        }

        // Item Of Page
        private void identity() {
            for (int i = 0; i < ed_id.size(); i++)
                editTexts.add((EditText) findViewById(ed_id.get(i)));
            question=findViewById(R.id.question);
            file=findViewById(R.id.file_choose);
            lv=findViewById(R.id.level);
        }


        private void hide_show(){
            if(!Save.getType().equals("0"))

                for (int i = 0; i < ed_id.size()-1; i++)
                    editTexts.get(i).setVisibility(View.INVISIBLE);

        }

        private void point(){
            FirebaseDatabase.getInstance().getReference().child("point")
                    .child(email.substring(0,email.length()-4)).child("question")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                int questions=Integer.parseInt(snapshot.getValue().toString())+1;
                                FirebaseDatabase.getInstance().getReference().child("point")
                                        .child(email.substring(0,email.length()-4))
                                        .child("question").setValue(questions);
                            }
                            else{
                                FirebaseDatabase.getInstance().getReference().child("point")
                                        .child(email.substring(0,email.length()-4))
                                        .child("question").setValue("1");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

    public void add_question_l(View view) {
        Check_Text checkText=new Check_Text(getApplicationContext());
            if((!Save.getType().equals("0")&&!checkText.is_empty(question))||
                    (Save.getType().equals("0")&&!checkText.is_empty(question)&&!checkText.is_empty(question))) {
                Question question = new Question();
                String id = String.valueOf(System.nanoTime());
                question.setName(name);
                question.setEmail(email);
                question.setFile(id);
                question.setType(Save.getType());
                question.setSubject(level);
                question.setLevel(lv.getText().toString());
                question.setQues(this.question.getText().toString());
                question.setCh_1(ans[0]);
                question.setCh_2(ans[1]);
                question.setCh_3(ans[2]);
                question.setCh_4(ans[3]);
                question.setCorrect_ans(ans[4]);
                op_save ops = new op_save();
                if (sort.equals("file"))
                    ops.upload_file(id, folder);
                
                FirebaseDatabase.getInstance().getReference().child("Questions").push().setValue(question);
            point();
                finish();
            }
            else
                Toast.makeText(getApplicationContext(),"Find Problem",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            if (resultCode == Activity.RESULT_OK) {

                //             textTargetUri.setText(targetUri.toString());
                Uri targetUri = data.getData();
                folder = targetUri;
                sort="file";
            }
        }
    }

    Uri folder;
        String sort="null";
}
