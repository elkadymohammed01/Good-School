package com.example.easyschool.first_page;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.easyschool.Main2Activity;
import com.example.easyschool.R;
import com.example.easyschool.data.myDbAdapter;
import com.example.easyschool.login_page;
import com.google.android.gms.common.internal.Objects;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class log_in extends Fragment {
    EditText []editTexts=new EditText[2];
    LinearLayout linearLayout;
    TextView textView;
    Button button;
    ImageView[]imageViews=new ImageView[3];
    float alf = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_log_in, container, false);
      translate(v);
      log_in(v);
        return v;
    }
    void translate(View v){
        button =v.findViewById(R.id.button);
        textView =v.findViewById(R.id.forget);
        textView =v.findViewById(R.id.forget);
        editTexts[0]=v.findViewById(R.id.editText);
        editTexts[1]=v.findViewById(R.id.editText2);
        linearLayout =v.findViewById(R.id.tool_bar);
        imageViews[0]=v.findViewById(R.id.gm);
        imageViews[1]=v.findViewById(R.id.tw);
        imageViews[2]=v.findViewById(R.id.fb);

        for(int i=0;i<2;i++)
            editTexts[i].setTranslationX(300);
        for(int i=0;i<2;i++)
            editTexts[i].setAlpha(alf);
        textView.setTranslationX(300);
        textView.setAlpha(alf);
        button.setTranslationX(300);
        button.setAlpha(alf);
        linearLayout.setTranslationX(300);
        linearLayout.setAlpha(alf);

        for(int i=0;i<2;i++)
            editTexts[i].animate().translationX(0).alpha(1).setDuration(400).setStartDelay(200*(i+1)).start();
        textView.animate().translationX(0).alpha(1).setDuration(400).setStartDelay(800).start();
        button.animate().translationX(0).alpha(1).setDuration(400).setStartDelay(600).start();
        linearLayout.animate().translationX(0).alpha(1).setDuration(400).setStartDelay(0).start();
            for(int i=0;i<3;i++)
                imageViews[i].setTranslationY(300);
            for(int i=0;i<3;i++)
                imageViews[i].setAlpha(alf);
            imageViews[0].animate().translationY(0).alpha(1).setDuration(400).setStartDelay(1500).start();
            imageViews[1].animate().translationY(0).alpha(1).setDuration(300).setStartDelay(1900).start();
            imageViews[2].animate().translationY(0).alpha(1).setDuration(300).setStartDelay(2200).start();
        }
        void log_in(View view){
        button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProgressBar bar= view.findViewById(R.id.bar_al);
                    bar.setVisibility(View.VISIBLE);
                   Query q= FirebaseDatabase.getInstance().getReference().child("Users")
                           .orderByChild("email").equalTo(editTexts[0].getText().toString());
                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot sn) {
                            if(sn.exists()){
                                String pass=editTexts[1].getText().toString();
                                for(DataSnapshot snapshot: sn.getChildren())
                                if(pass.equals(snapshot.child("password").getValue().toString())){
                                    myDbAdapter mbda=new myDbAdapter(getContext());
                                    mbda.insertData(snapshot.child("name").getValue().toString(),
                                            snapshot.child("phone").getValue().toString(),
                                            (editTexts[0].getText().toString()),
                                            snapshot.child("password").getValue().toString(),
                                            snapshot.child("level").getValue().toString()
                                            );
                                startActivity(new Intent(getContext(), Main2Activity.class));
                                getActivity().finish();
                                break;}
                                else {
                                    Toast.makeText(getContext(), "Check Password Again", Toast.LENGTH_SHORT).show();
                                    bar.setVisibility(View.INVISIBLE);
                                    break;
                                }
                            }
                            else {
                                bar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "Can't Find Users", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                }
            });
        }
}
