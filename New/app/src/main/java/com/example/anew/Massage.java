package com.example.anew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anew.data.Server;
import com.example.anew.data.comment;
import com.example.anew.data.singletoon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Massage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massage);
        getSupportActionBar().setTitle(singletoon.server.name);
        final EditText edt=findViewById(R.id.edt_chat);
        ListView lst=findViewById(R.id.list_chat);
        stro.name=findViewById(R.id.to_p);
        stro.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stro.mt="";
                stro.name.setText("");
            }
        });
        find(lst);
        edt.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {


                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || event.getAction() == KeyEvent.ACTION_DOWN
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            try{ oncommentAction(v);}
                            catch (Exception ex){
                                Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();}
                            edt.setText("");
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

    }

    void find (final ListView listView){

        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("Comment").child(singletoon.id);
        Query query=dbr;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    int x=0;ArrayList<String> id=new ArrayList<>();
                    ArrayList<comment>comments=new ArrayList<>();
                    try {
                        for(DataSnapshot dd:dataSnapshot.getChildren()){

                       comment comment=dataSnapshot.getValue( comment.class);
                        comment.name=dd.child("name").getValue().toString();
                        comment.love=dd.child("love").getValue().toString();
                        comment.mail=dd.child("mail").getValue().toString();
                        comment.mtion=dd.child("mtion").getValue().toString();
                        comment.comment=dd.child("comment").getValue().toString();
                        comments.add(comment);
                        id.add(dd.getKey().toString());

                    }
                    stro.id=id;
                    }

                    catch(Exception ex){
                        Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                    }
                    stro.comments=comments;
                    listView.setAdapter(new Adapter(getApplicationContext()));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void oncommentAction(TextView v) {
        String Text =v.getText().toString();

writeNewComment(FirebaseDatabase.getInstance().getReference(),Text,singletoon.id,singletoon.name,singletoon.mail,stro.mt);
    }
    public void writeNewComment(DatabaseReference databaseReference,String Text, String userId, String name, String email, String who) {
       comment comment=new comment(name,email,Text,who,"0");
       String id_po=String.valueOf(Long.MAX_VALUE-System.nanoTime());
        databaseReference.child("Comment").child(userId).child(id_po).setValue(comment);
      //  FirebaseDatabase.getInstance().getReference().child("note").child(singletoon.server.email.substring(0, singletoon.server.email.length() - 4)).child("Comment").child(singletoon.id).push().setValue(new com(singletoon.name,singletoon.mail,Text));
stro.comments.add(comment);
stro.id.add(id_po);
    }
}
class com{
    String name , email , message;

    public com(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public com() {
    }
}
        class  stro{
    static ArrayList<comment>comments=new ArrayList<>();
    static ArrayList<String>id=new ArrayList<>();
    static String mt="";
    static  TextView name;
    static Map<String,Bitmap>profile=new HashMap<>();
        }
class Adapter extends BaseAdapter {
    Context context;
    Adapter(Context context){
        this.context=context; }
    @Override
    public int getCount() {
        return stro.comments.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context) ;
        if(position==stro.comments.size()){
            convertView = inflater.inflate(R.layout.line, null);
            return  convertView;
        }
        convertView = inflater.inflate(R.layout.chat_item, null);
        final comment comment_cont=stro.comments.get(position);
        final TextView name =convertView.findViewById(R.id.comment_name),
                mt=convertView.findViewById(R.id.men),
                comment=convertView.findViewById(R.id.comment_text),
                love_comment =convertView.findViewById(R.id.love_comment),
                replay =convertView.findViewById(R.id.rep_comment);
        love_found(stro.id.get(position),singletoon.mail,love_comment,false,comment_cont);
        love_comment.setText(comment_cont.love);
        name.setText(comment_cont.name);
        mt.setText(comment_cont.mtion);
        comment.setText(comment_cont.comment);
replay.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        stro.mt=comment_cont.name;
        if(stro.mt==singletoon.name)stro.mt="";
        stro.name.setText("x           reply to "+stro.mt ) ;
    }
});
final de.hdodenhof.circleimageview.CircleImageView cr =convertView.findViewById(R.id.prof_ph);
love_comment.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        love_found(stro.id.get(position),singletoon.mail,love_comment,!false,comment_cont);
    }
});
if(stro.profile.containsKey(comment_cont.mail)){cr.setImageBitmap(stro.profile.get(comment_cont.mail));}
else{
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    final ProgressDialog progressDialog
            = new ProgressDialog(context);
    StorageReference islandRef = storageRef.child(comment_cont.mail+"/me");

    final long ONE_MEGABYTE = 1024 * 1024;
    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new
                                                                  OnSuccessListener<byte[]>() {
                                                                      @Override
                                                                      public void onSuccess(byte[] bytes) {
                                                                          Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                                                                          stro.profile.put(comment_cont.mail,bitmap);
                                                                          cr.setImageBitmap(bitmap); }
                                                                  }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
        }
    });
}
        return convertView;
    }
    void love_found(final String id, final String email, final TextView V, final boolean B, final comment comment){
        DatabaseReference dbr= FirebaseDatabase.getInstance().getReference().child("love_comment")
                .child(id).child(email.substring(0,email.length()-4));
        Query query=dbr;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    V.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_favorite_black_24dp),null,null,null);
                }
                else if(B){
                    V.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_favorite_black_24dp),null,null,null);
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("love_comment").child(id);
                    databaseReference.child(email.substring(0,email.length()-4)).setValue("1");
                    String S=(comment.love);
                    int x=0+(Integer.parseInt(S));
                    comment.love= String.valueOf(x+1);
                    V.setText(String.valueOf(x+1));
                    V.setText(comment.love);
                    DatabaseReference  dbR =
                            FirebaseDatabase.getInstance().getReference();
                    dbR.child("Comment").child(singletoon.id).child(id).setValue(comment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}