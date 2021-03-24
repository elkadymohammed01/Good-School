/*
 * Copyright (c) 2020. rogergcc
 */

package com.example.easyschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyschool.R;
import com.example.easyschool.Video;
import com.example.easyschool.data.Save;
import com.example.easyschool.model.CourseCard;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class lessonAdapter extends RecyclerView.Adapter<lessonAdapter._ViewHolder> {

    Context mContext;
    private List<CourseCard> mData;
    private CoursesItemClickListener coursesItemClickListener;

    public lessonAdapter(Context mContext, List<CourseCard> mData) {
        this.mContext = mContext;
        this.mData=mData;
    }

    @NonNull
    @Override
    public _ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popular_course,viewGroup,false);
        return new _ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final _ViewHolder viewHolder, final int i) {
        viewHolder.mItem = mData.get(i);
        final int pos = viewHolder.getAdapterPosition();
        viewHolder.itemView.setTag(pos);

        viewHolder.course.setText(mData.get(i).getFile_type());
        viewHolder.quantity_courses.setText(mData.get(i).getQuantityCourses());
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Save.setId(mData.get(i).getFile_id());
                mContext.startActivity(new Intent(mContext,Video.class));
            }
        });


    }


    public int getDimensionValuePixels(int dimension)
    {
        return (int) mContext.getResources().getDimension(dimension);
    }



    @Override
    public long getItemId(int position) {
        CourseCard courseCard = mData.get(position);
        return courseCard.getId();
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class _ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView course;
        TextView quantity_courses;
        CardView card_item;
        public CourseCard mItem;
        public View view;
        public _ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_item = itemView.findViewById(R.id.card_item);
            imageView = itemView.findViewById(R.id.image);
            quantity_courses = itemView.findViewById(R.id.lesson_title);
            course = itemView.findViewById(R.id.subject_title);
            view=itemView;
        }

        void setPostImage(CourseCard courseCard){
            imageView.setImageResource(courseCard.getImageCourse());
        }
    }
}
