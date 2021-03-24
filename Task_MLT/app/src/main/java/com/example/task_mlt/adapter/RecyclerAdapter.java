package com.example.task_mlt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.task_mlt.R;
import com.example.task_mlt.data.Event;

import java.util.List;

/**
 * Created by chintu gandhwani on 1/22/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {


    List<Event> productsList;
    Context context;


    public RecyclerAdapter()
    {

    }

    public RecyclerAdapter(List<Event> productsList, Context mContext) {
        this.productsList = productsList;

        this.context = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        return new MyViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.Title.setText(productsList.get(position).name);
        holder.details.setText(productsList.get(position).information);
        holder.manger.setText(productsList.get(position).manger);
       holder.time.setText(productsList.get(position).time);
    }

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
         ImageView place_image;
        TextView Title,details,manger,time;

        public MyViewHolder(View root) {
            super(root);
            Title=root.findViewById(R.id.name);
            details=root.findViewById(R.id.details_text);
            manger=root.findViewById(R.id.manger);
            time=root.findViewById(R.id.to_me);
           place_image=root.findViewById(R.id.evt_image);
    }
}
