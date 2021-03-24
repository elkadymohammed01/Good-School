package com.example.easyschool.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyschool.R;
import com.example.easyschool.data.Check_Text;
import com.example.easyschool.data.Question;
import com.example.easyschool.data.myDbAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class question_adapter extends RecyclerView.Adapter<Holder> {


    private List<Question> questions;
    private List<String> ids;
    public Activity activity;
    private Context context;
    public question_adapter()
    {

    }

    public question_adapter(List<Question> questions, Context mContext,List<String> ids) {
        this.questions = questions;
        this.context = mContext;
        this.ids=ids;
        inf();
    }

    //Personal Information
    private String name,phone,email,level;
    private void inf(){
        myDbAdapter mydb = new myDbAdapter(context);
        String all[]=mydb.getData_inf();
        name=all[0];email=all[1];phone=all[2];level=all[3];
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= null;
        if(viewType==0){view= LayoutInflater.from(parent.getContext()).inflate(R.layout.que_one,parent,false);
        }
        else {view= LayoutInflater.from(parent.getContext()).inflate(R.layout.que_two,parent,false);
        }

        return new Holder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(questions.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }


    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        holder.title.setText(questions.get(position).getQues());
        if(Integer.parseInt(questions.get(position).getType())==0){

            holder.identity_one();

            holder.choose[0].setText(questions.get(position).getCh_1()+"    ");
            holder.choose[1].setText(questions.get(position).getCh_2()+"    ");
            holder.choose[2].setText(questions.get(position).getCh_3()+"    ");
            holder.choose[3].setText(questions.get(position).getCh_4()+"    ");
            holder.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<4;i++)
                        if(holder.choose[i].isChecked()
                                &&Integer.parseInt(questions.get(position).getCorrect_ans())==i+1){
                            FirebaseDatabase.getInstance().getReference().child("Qid")
                                    .child(email.substring(0,email.length()-4))
                                    .child(ids.get(position)).setValue(email);
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
                            removeAt(position);
                        }
                    else if(holder.choose[i].isChecked())
                            Toast.makeText(context, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else {
            holder.identity_two();
            final Check_Text checkText=new Check_Text(context);
            holder.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!checkText.is_empty(holder.text)){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Qid").child(email.substring(0,email.length()-4)).child(ids.get(position))
                            .setValue(email);

                      showDialog(activity, questions.get(position).getCorrect_ans());

                                removeAt(position);

                    }
                    else
                        Toast.makeText(context,"Answer is empty .",Toast.LENGTH_LONG).show();

                }
            });
        }


    }

    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.answer_card);

        TextView ok =dialog.findViewById(R.id.ok);
        TextView answer =dialog.findViewById(R.id.answer);
        answer.setText(msg);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public void removeAt(int position) {
        questions.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, questions.size());
    }
}


class Holder extends RecyclerView.ViewHolder{

    ImageView icon;
    TextView title ,done;
    EditText text;
    View root;
    RadioButton[]choose=new RadioButton[4];
    public Holder(View root) {
        super(root);
        identity(root);
        this.root=root;
    }

    private void identity(View view) {
        title=view.findViewById(R.id.question_title);
        done=view.findViewById(R.id.submit);
    }
    void identity_one(){
        choose[0]=root.findViewById(R.id.ch_1);
        choose[1]=root.findViewById(R.id.ch_2);
        choose[2]=root.findViewById(R.id.ch_3);
        choose[3]=root.findViewById(R.id.ch_4);
    }
    void identity_two() {
        text=root.findViewById(R.id.ans_text);
    }

}

