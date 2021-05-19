package com.maad.bankticket.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maad.bankticket.R;

import java.util.List;

public class HomePhotoAdapter
extends  RecyclerView.Adapter<HomePhotoAdapter.ViewHolder>{
    List<HomePhoto> items;

    public  HomePhotoAdapter (List<HomePhoto> items){
        this.items=items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HomePhoto item = items.get(position);
            holder.title.setText(item.getTitle());
            holder.image.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);


        }
    }
}





















