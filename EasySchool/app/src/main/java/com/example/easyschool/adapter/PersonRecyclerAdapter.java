/*
 * Copyright (c) 2020. rogergcc
 */

package com.example.easyschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyschool.R;
import com.example.easyschool.data.Save;
import com.example.easyschool.data.User;
import com.example.easyschool.data.op_save;
import com.example.easyschool.model.CourseCard;
import com.example.easyschool.other_user;

import java.util.List;

public class PersonRecyclerAdapter extends RecyclerView.Adapter<PersonRecyclerAdapter._ViewHolder> {

    Context mContext;
    private List<CourseCard> mData;
    private CoursesItemClickListener coursesItemClickListener;
    private List<User> users;

    public PersonRecyclerAdapter(Context mContext, List<CourseCard> mData, CoursesItemClickListener listener,List<User> users) {
        this.mContext = mContext;
        this.mData = mData;
        this.coursesItemClickListener = listener;
        this.users=users;
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

        op_save op=new op_save();
        op.download_file(users.get(i).getEmail(),viewHolder.imageView);

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
                coursesItemClickListener.onDashboardCourseClick(mData.get(i), viewHolder.imageView);

                Save.setUser(users.get(i));
                Intent lop=new Intent(mContext, other_user.class);
                mContext.startActivity(lop);
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
