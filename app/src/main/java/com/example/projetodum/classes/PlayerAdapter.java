package com.example.projetodum.classes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetodum.R;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private Context context;
    private ArrayList<Exercises> list;

    public PlayerAdapter(Context context, ArrayList<Exercises> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item3, parent, false);
        return new PlayerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Exercises exercises = list.get(position);

        holder.exerciseName.setText(exercises.getName());
        holder.exerciseCalories.setText(String.valueOf(exercises.getCalories()));
        holder.exerciseDescription.setText(exercises.getDescription());

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.favorite.setBackground(Drawable.createFromPath("@drawable/ic_baseline_favorite_ticked_24"));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder{
        TextView exerciseName, exerciseCalories, exerciseDescription;
        Button favorite;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.eName);
            exerciseCalories = itemView.findViewById(R.id.eCalories);
            exerciseDescription = itemView.findViewById(R.id.eDescription);
            favorite = itemView.findViewById(R.id.eFavorite);
        }
    }
}
