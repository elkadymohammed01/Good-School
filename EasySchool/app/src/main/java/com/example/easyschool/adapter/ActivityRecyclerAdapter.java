package com.example.easyschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyschool.R;
import com.example.easyschool.data.Activities;
import com.example.easyschool.data.Save;
import com.example.easyschool.details;

import java.util.List;

/**
 * Created by Mohammed M Elkady on 12/2/2020.
 */

public class ActivityRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private List<Activities> activities;
    private Context context;
    private List<String>id;
   Drawable[]icon=new Drawable[6];
    public ActivityRecyclerAdapter()
    {

    }

    public ActivityRecyclerAdapter(List<Activities> activities, Context mContext,List<String> id) {
        this.activities = activities;
        icon[5]=mContext.getResources().getDrawable(R.drawable.activities2);
        icon[0]=mContext.getResources().getDrawable(R.drawable.activities);
        icon[2]=mContext.getResources().getDrawable(R.drawable.family_help);
        icon[3]=mContext.getResources().getDrawable(R.drawable.activities3);
        icon[4]=mContext.getResources().getDrawable(R.drawable.activities4);
        icon[1]=mContext.getResources().getDrawable(R.drawable.activities5);
        this.context = mContext;
        this.id=id;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_frutorials,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return activities.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    holder.icon.setImageDrawable(icon[position%icon.length]);
    holder.title.setText(activities.get(position).getTitle());
    holder.all_Item.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Save.setId(id.get(position));
            Save.setId_photo(icon[position%icon.length]);
            Save.setActivities(activities.get(position));
            context.startActivity(new Intent(context, details.class));

        }
    });
    }
}


class MyViewHolder extends RecyclerView.ViewHolder{

    CardView all_Item;
    ImageView icon;
    TextView title;
    public MyViewHolder(View root) {
        super(root);
        identity(root);
}

    private void identity(View view) {
        title =view.findViewById(R.id.title);
        icon =view.findViewById(R.id.youtubeThubnail);
        all_Item =view.findViewById(R.id.card_view);
    }

}
