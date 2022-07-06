package com.example.projetodum.classes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetodum.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private ArrayList<Exercises> list;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    public PlayerAdapter(Context context, ArrayList<Exercises> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item3, parent, false);
        return new PlayerViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {

        Exercises exercise = list.get(position);

        holder.exerciseName.setText(exercise.getName());
        holder.exerciseCalories.setText(String.valueOf(exercise.getCalories()) + " calories/minute");
        holder.exerciseDescription.setText(exercise.getDescription());
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        /*mDatabase.getReference("UserLike").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if(dataSnapshot.getValue().toString().equalsIgnoreCase(exercise.getEid())) {
                            holder.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_ticked_24);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());
            }
        });*/




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder{
        TextView exerciseName, exerciseCalories, exerciseDescription;
        Button favorite;

        public PlayerViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.eName);
            exerciseCalories = itemView.findViewById(R.id.eCalories);
            exerciseDescription = itemView.findViewById(R.id.eDescription);
            favorite = itemView.findViewById(R.id.eFavorite);

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        int pos = getBindingAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos, favorite);
                        }
                    }
                }
            });


        }
    }
}
