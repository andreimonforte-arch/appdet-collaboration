package com.example.activatehooparena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class HoophubAdapter extends RecyclerView.Adapter<HoophubAdapter.ViewHolder> {

    private List<Court> courts;
    private List<Court> courtsFull;
    private OnCourtClickListener listener;

    public interface OnCourtClickListener {
        void onCourtClick(Court court);
    }

    public HoophubAdapter(List<Court> courts, OnCourtClickListener listener) {
        this.courts = new ArrayList<>(courts);
        this.courtsFull = new ArrayList<>(courts);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_court, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Court court = courts.get(position);
        holder.tvName.setText(court.getName());
        holder.tvLocation.setText(court.getLocation());
        holder.itemView.setOnClickListener(v -> listener.onCourtClick(court));
    }

    @Override
    public int getItemCount() {
        return courts.size();
    }

    public void filter(String text) {
        courts.clear();
        if (text.isEmpty()) {
            courts.addAll(courtsFull);
        } else {
            text = text.toLowerCase();
            for (Court court : courtsFull) {
                if (court.getName().toLowerCase().contains(text) ||
                        court.getLocation().toLowerCase().contains(text)) {
                    courts.add(court);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLocation;
        ImageView ivImage;

        ViewHolder(View itemView) {
            super(itemView);
            // FIX: Use correct IDs from item_court.xml
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            ivImage = itemView.findViewById(R.id.ivImage);  // ← FIXED from tvVersion
        }
    }
}