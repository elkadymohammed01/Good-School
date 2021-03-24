package com.example.easyschool.first_page;

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

import androidx.fragment.app.Fragment;

import com.example.easyschool.R;
import com.example.easyschool.data.Check_Text;
import com.example.easyschool.data.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Sign_Up extends Fragment {
    EditText name,mail,password,phone,level;
    Check_Text text;
    Button button ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v= inflater.inflate(R.layout.fragment_sign_up, container, false);
        identity(v);
        add_user(v);
        return v;
    }
    private void identity(View v){

    text = new Check_Text(getContext());
    name=v.findViewById(R.id.editText6);
    mail=v.findViewById(R.id.editText5);
    password=v.findViewById(R.id.editText2);
    phone=v.findViewById(R.id.editText4);
    level=v.findViewById(R.id.editText3);
    button=v.findViewById(R.id.button);
    }
    private void add_user(View v){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!text.is_empty(name) &&
                        !text.is_empty(mail) &&
                        !text.is_empty(phone) &&
                        !text.is_empty(level) &&
                        !text.is_empty(password) &&
                        !text.is_empty(level)
                ) {
                    User user = new User();
                    user.setEmail(mail.getText().toString());
                    user.setLevel(level.getText().toString());
                    user.setName(name.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setPhone(phone.getText().toString());
                    ((ProgressBar) v.findViewById(R.id.bar)).setVisibility(View.VISIBLE);
                    if (text.is_Integer(user.getLevel()) && Integer.parseInt(user.getLevel()) >= 0
                            && Integer.parseInt(user.getLevel()) < 13) {
                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(user.getEmail().substring(0, user.getEmail().length() - 4))
                                .setValue(user);
                        Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
                    } else {
                        ArrayList<String> word = new ArrayList<>();

                        word.add("Science");
                        word.add("History");
                        word.add("English");
                        word.add("Math");
                        word.add("Arabic");
                        word.add("Music");
                        word.add("Computer");
                        boolean is = false;
                        if (user.getLevel().length() > 2) {
                            for (int i = 0; i < word.size(); i++)
                                if (word.get(i).toUpperCase().charAt(0) == user.getLevel().toUpperCase().charAt(0)
                                        && word.get(i).toUpperCase().charAt(1) == user.getLevel().toUpperCase().charAt(1)) {
                                    user.setLevel(word.get(i));
                                    is = true;
                                }
                        }

                        if (is) { FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(user.getEmail().substring(0, user.getEmail().length() - 4))
                                        .setValue(user);

                            Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
                            }

                        else
                            Toast.makeText(getContext(), "You Have Problem in Input Level type" +
                                        "perent Use Code 0", Toast.LENGTH_LONG).show();


                    }
                } else
                    Toast.makeText(getContext(), "You Have Problem in Input", Toast.LENGTH_LONG).show();

                ((ProgressBar) v.findViewById(R.id.bar)).setVisibility(View.INVISIBLE);
            }

        });


    }

}
