package com.maad.bankticket.model;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maad.bankticket.Settings;
import com.maad.bankticket.NearestBranch;
import com.maad.bankticket.R;
import com.maad.bankticket.TakeTurn;
import com.maad.bankticket.Ticket;

import java.util.List;

public class HomePhotoAdapter
        extends RecyclerView.Adapter<HomePhotoAdapter.ViewHolder> {

    List<HomePhoto> items;
    Activity activity;

    public HomePhotoAdapter(Activity activity, List<HomePhoto> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomePhoto item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.image.setImageResource(item.getImage());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Intent i0 = new Intent(activity, TakeTurn.class);
                        activity.startActivity(i0);
                        break;
                    case 1:
                        Intent i1 = new Intent(activity, Ticket.class);
                        activity.startActivity(i1);
                        break;
                    case 2:
                        Intent i2 = new Intent(activity, NearestBranch.class);
                        activity.startActivity(i2);
                        break;
                    case 3:
                        Intent i3 = new Intent(activity, Settings.class);
                        activity.startActivity(i3);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}





















