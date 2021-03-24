package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyschool.data.Activities;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.Task;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.data.op_save;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Task_details extends AppCompatActivity {

    TextView Title ,details ,Teacher_name;
    ImageView imageView;
    String id ="";
    Task task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        identity();
        set_details();
    }
    // Item Of Page
    private void identity() {
        task= Save.getTask();
        imageView=findViewById(R.id.imageView8);
        Title=findViewById(R.id.title_template);
        details=findViewById(R.id.details_template);
        Teacher_name=findViewById(R.id.name_temp);

        inf();
        id=Save.getId();
    }

    private void set_details(){
        Title.setText(task.getTitle());
        details.setText(task.getDetails());
        imageView.setImageDrawable(Save.getId_photo());
        Teacher_name.setText(task.getSubject());
    }

    public void go_back(View view) {
        finish();
    }
    //Personal Information
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }

    public void sent(View view) {

        get_data(new String[]{"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                "text/plain",
                "application/pdf",
                "*/*"});
    }
    private void get_data(String[] lop){
        try {
            String[] mimeTypes =
                    lop;

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                if (mimeTypes.length > 0) {
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
            } else {
                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }
                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
            }
            startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 100);

        }
        catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    boolean has_file=false;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            if (resultCode == Activity.RESULT_OK) {

                //             textTargetUri.setText(targetUri.toString());
                Uri targetUri = data.getData();
                file = targetUri;
                has_file=true;
                String id =String.valueOf(System.nanoTime());

                FirebaseDatabase.getInstance().getReference().child("Sent_Task")
                        .child(task.getTeacher_email().substring(0,task.getTeacher_email().length()-4))
                        .child(Save.getId()).child(email.substring(0,email.length()-4))
                        .setValue(id);

                FirebaseDatabase.getInstance().getReference().child("Task_op")
                        .child(email.substring(0,email.length()-4)).child(Save.getId())
                        .setValue(Save.getId());

                op_save op =new op_save();
                op.upload_file(id,file);

                FirebaseDatabase.getInstance().getReference().child("point")
                        .child(email.substring(0,email.length()-4))
                        .child("task").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int val=0;
                        if(snapshot.exists()){
                            val=Integer.parseInt(snapshot.getValue().toString());
                        }
                        val++;
                        FirebaseDatabase.getInstance().getReference().child("point")
                                .child(email.substring(0,email.length()-4))
                                .child("task").setValue(val);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        }
    }

    Uri file;

}
