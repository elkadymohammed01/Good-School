package com.example.easyschool.matchcourses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyschool.R;

import java.time.Instant;
import java.util.List;

/**
 * Created by Mohammed M Elkady on 07.02.2021.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<Item> data;

    public ShopAdapter(List<Item> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_shop_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item itemDatos = data.get(position);
        holder.image.setImageResource(itemDatos.getImage());
        holder.tv_titulo.setText(itemDatos.getName());
        holder.tv_cantidad_cursos.setText(itemDatos.getPrice());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        public TextView tv_titulo;
        public TextView tv_cantidad_cursos;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            tv_titulo = itemView.findViewById(R.id.tv_titulo);
            tv_cantidad_cursos = itemView.findViewById(R.id.tv_cantidad_cursos);
        }
    }
}
