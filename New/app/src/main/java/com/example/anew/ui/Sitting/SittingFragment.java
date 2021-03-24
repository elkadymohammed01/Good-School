package com.example.anew.ui.Sitting;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.anew.Gallery;
import com.example.anew.MainActivity;
import com.example.anew.R;
import com.example.anew.data.myDbAdapter;
import com.example.anew.data.singletoon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;

public class SittingFragment extends Fragment {

    TextView Name,Ed_name,Ed_phone,Ed_mail;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_sitting, container, false);
        final RelativeLayout gallery=root.findViewById(R.id.go_to_gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Gallery.class));
            }
        });
        Name=root.findViewById(R.id.dataTextView_name);
        Name.setText(singletoon.name);
        Ed_mail=root.findViewById(R.id.edemail);
        Ed_mail.setText(singletoon.mail);
        Ed_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialo = new Dialog(getContext());
                dialo.setContentView(R.layout.edit_data);
                dialo.setTitle("Edit Name");
                dialo.show();
                final EditText et =(EditText)dialo.findViewById(R.id.edit_data);
                ((Button)dialo.findViewById(R.id.dodo)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialo.cancel();
                    }
                });

            }
        });
        LinearLayout layout=root.findViewById(R.id.log_out_me);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDbAdapter myone=new myDbAdapter(getContext());
                myone.delete(singletoon.name);
                Toast.makeText(getContext(),"Good Bye",Toast.LENGTH_LONG).show();
                singletoon.mail="";
                startActivity(new Intent(getContext(),MainActivity.class));
                getActivity().finish();
            }
        });
        Ed_name=root.findViewById(R.id.edname);
        Ed_name.setText(singletoon.name);
        Ed_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialo = new Dialog(getContext());
                dialo.setContentView(R.layout.edit_data);
                dialo.setTitle("Edit Name");
                dialo.show();
                final EditText et =(EditText)dialo.findViewById(R.id.edit_data);
                ((Button)dialo.findViewById(R.id.dodo)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("Users").child(singletoon.mail.substring(0, singletoon.mail.length() - 4))
                                .child("userName").setValue(et.getText().toString());
                                                dialo.cancel();
                }
                });

            }
        });
        Ed_phone=root.findViewById(R.id.edphone);
        Ed_phone.setText(singletoon.phone);
        Ed_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialo = new Dialog(getContext());
                dialo.setContentView(R.layout.edit_data);
                dialo.setTitle("Edit Phone");
                dialo.show();

                final EditText et =(EditText)dialo.findViewById(R.id.edit_data);
                et.setOnEditorActionListener(
                        new EditText.OnEditorActionListener() {


                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                // Identifier of the action. This will be either the identifier you supplied,
                                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                                if (actionId == EditorInfo.IME_ACTION_SEARCH
                                        || actionId == EditorInfo.IME_ACTION_DONE
                                        || event.getAction() == KeyEvent.ACTION_DOWN
                                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                                    dialo.cancel();
                                    return true;
                                }
                                // Return true if you have consumed the action, else false.
                                return false;
                            }
                        });
            }
        });
        final TextView love_all=root.findViewById(R.id.love_all),view_all=root.findViewById(R.id.view_all);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("love_all").child(singletoon.mail.substring(0,singletoon.mail.length()-4));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    if(snapshot.getValue().toString().length()<=3)
                        love_all.setText(snapshot.getValue().toString());
                    else  love_all.setText(snapshot.getValue().toString().charAt(0)+"."+snapshot.getValue().toString().charAt(1)+"K");

                else{love_all.setText("0");}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference().child("view_all").child(singletoon.mail.substring(0,singletoon.mail.length()-4));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    if(snapshot.getValue().toString().length()<=3)
                        view_all.setText(snapshot.getValue().toString());
                    else  view_all.setText(snapshot.getValue().toString().charAt(0)+"."+snapshot.getValue().toString().charAt(1)+"K");

                else{view_all.setText("0");}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       return  root;
    }
}