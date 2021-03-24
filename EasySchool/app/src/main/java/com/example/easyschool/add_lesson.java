package com.example.easyschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyschool.data.Check_Text;
import com.example.easyschool.data.lesson;
import com.example.easyschool.data.myDbAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;

public class add_lesson extends AppCompatActivity {
EditText title,details,level;
TextView post;
String sort ="details" ,for_me="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
         identity();
         color_post();
         inf();
    }

    void identity(){
        title=findViewById(R.id.lesson_title);
        details=findViewById(R.id.lesson_details);
        level=findViewById(R.id.level);
        post =findViewById(R.id.posting);
    }
    void color_post(){
        use_this(title,0);
        use_this(level,1);
    }
    int type[]={0,0};
    int use_this(EditText text, final int x) {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    type[x]++;
                } else type[x] = 0;
                if (type[0] > 0 && type[1] > 0)
                    post.setTextColor(getResources().getColor(R.color.primaryTextColor));
                else
                    post.setTextColor(getResources().getColor(R.color.grey));
            }
        });
    return 0;
    }

    public void pdf(View view) {
        for_me="file";
        get_data(new String[]{"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                            "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                            "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                            "text/plain",
                            "application/pdf",
                            "*/*"});
    }

    public void images(View view) {
        for_me="image";
        get_data(new String[]{"image/*"});
    }

    public void video(View view) {
        for_me="video";
        get_data(new String[]{"video/*"});
    }
    void get_data(String[] lop){
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            if (resultCode == Activity.RESULT_OK) {

                //             textTargetUri.setText(targetUri.toString());
                Uri targetUri = data.getData();
                file = targetUri;
                sort=for_me;
            }
        }
    }

    Uri file;

    private void upload_file(String f){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child(f);

        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(add_lesson.this, exception.toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(add_lesson.this, "exception.toString()", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void add_lesson(View view) {
     if(type[0]>0&&type[1]>0){
     lesson lesson =new lesson();
     lesson.setEmail(email);
     lesson.setDetails(details.getText().toString());
     lesson.setLevel(level.getText().toString());
     lesson.setType(sort);
     lesson.setName(name);
     lesson.setSubject(lv);
     lesson.setTitle(title.getText().toString());
     if(sort.equals("details"))
     lesson.setFile("0");
     else{
         String file=String.valueOf(System.nanoTime());
         lesson.setFile(file);
         upload_file(file);
     }

     point();
         FirebaseDatabase.getInstance().getReference().child("Lesson").push().setValue(lesson);
     }
     else{Toast.makeText(getApplicationContext(),"Level and  Title is empty",Toast.LENGTH_LONG).show();}
    }
    //Personal Information
    private String name,phone,email,lv;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];lv=all[3];
    }
}
