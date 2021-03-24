/*
 * Copyright (c) 2020. rogergcc
 */

package com.example.easyschool.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.easyschool.R;
import com.example.easyschool.Task_details;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.Task;
import com.example.easyschool.model.CStagged;
import com.example.easyschool.model.CourseCard;
import com.example.easyschool.person;

import java.util.List;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter._ViewHolder> {

    Context mContext;
    private List<CourseCard> mData;
    private CoursesItemClickListener coursesItemClickListener;
    private List<Task>tasks;

    public CourseRecyclerAdapter(Context mContext, List<CourseCard> mData, CoursesItemClickListener listener, List<Task>tasks) {
        this.mContext = mContext;
        this.mData = mData;
        this.coursesItemClickListener = listener;
        this.tasks=tasks;
    }

    @NonNull
    @Override
    public _ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_card,viewGroup,false);
        return new _ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final _ViewHolder viewHolder, final int i) {
        viewHolder.mItem = mData.get(i);
        final int pos = viewHolder.getAdapterPosition();
        viewHolder.itemView.setTag(pos);

        viewHolder.setPostImage(mData.get(i));
        viewHolder.course.setText(mData.get(i).getCourseTitle());

        viewHolder.quantity_courses.setText(mData.get(i).getQuantityCourses());

        if (i%2==1){

            int dimenTopPixeles = getDimensionValuePixels(R.dimen.staggedmarginbottom);
            int dimenleftPixeles = getDimensionValuePixels(R.dimen.horizontal_card);
            ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) viewHolder.card_item.getLayoutParams();
            cardViewMarginParams.setMargins(dimenleftPixeles, dimenTopPixeles, 0, 0);
            viewHolder.card_item.requestLayout();
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Save.isShow()){
                    Save.setType(mData.get(i).getFile_id());
                    mContext.startActivity(new Intent(mContext, person.class));
                    return;
                }
                coursesItemClickListener.onDashboardCourseClick(mData.get(i), viewHolder.imageView);
                Save.setTask(tasks.get(i));
                Save.setId_photo(mContext.getResources().getDrawable(mData.get(i).getImageCourse()));
                Save.setId(mData.get(i).getFile_id());
                mContext.startActivity(new Intent(mContext, Task_details.class));

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
        public _ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_item = itemView.findViewById(R.id.card_item);
            imageView = itemView.findViewById(R.id.card_view_image);
            course = itemView.findViewById(R.id.stag_item_course);
            quantity_courses = itemView.findViewById(R.id.stag_item_quantity_course);
        }

        void setPostImage(CourseCard courseCard){
            imageView.setImageResource(courseCard.getImageCourse());
        }
    }
}
