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
import android.widget.Toast;

import com.example.easyschool.data.myDbAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class edit_user extends AppCompatActivity {

    EditText Name_User,Level_User,Phone_User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        inf();
        identity();
    }
    //Personal Information
    private String name,phone="",email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }

    // Item Of Page
    private void identity() {
        Name_User=findViewById(R.id.user_name);
        Phone_User=findViewById(R.id.phone);
        Level_User=findViewById(R.id.level);

        set_def();
    }
    private void set_def(){
        Name_User.setText(name);
        Phone_User.setText(phone+"");
        Level_User.setText(level);
    }

    public void change_Name(View view) {
        String Name=Name_User.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(email.substring(0,email.length()-4)).child("name").setValue(Name);

        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        mydb.delete(name);
        name=Name;
        mydb.insertData(name,phone,email,"12",level);
        Toast.makeText(getApplicationContext(),"The Name is Edit",Toast.LENGTH_LONG).show();

    }

    public void change_Phone(View view) {

        String phone=Phone_User.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(email.substring(0,email.length()-4)).child("phone").setValue(phone);
        Toast.makeText(getApplicationContext(),"The Phone is Edit",Toast.LENGTH_LONG).show();

        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        mydb.delete(name);
        mydb.insertData(name,phone,email,"12",level);
    }

    public void change_Level(View view) {

        String lv=Level_User.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(email.substring(0,email.length()-4)).child("level").setValue(lv);
        Toast.makeText(getApplicationContext(),"The Level is Edit",Toast.LENGTH_LONG).show();

        myDbAdapter mydb = new myDbAdapter(getApplicationContext());
        mydb.delete(name);
        mydb.insertData(name,phone,email,"12",lv);
    }

    public void photo(View view) {
        get_data(new String[]{"image/*"});
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            if (resultCode == Activity.RESULT_OK) {

                //             textTargetUri.setText(targetUri.toString());
                Uri targetUri = data.getData();
                file = targetUri;
                upload_file(email);
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
                Toast.makeText(edit_user.this, exception.toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(edit_user.this, "exception.toString()", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
