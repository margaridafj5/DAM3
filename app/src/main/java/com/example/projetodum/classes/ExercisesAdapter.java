package com.example.projetodum.classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetodum.ExerciseSchedule;
import com.example.projetodum.R;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExercisesViewHolder> {

    private Context context;
    private ArrayList<Exercises> exerciseList;

    public ExercisesAdapter(Context context, ArrayList<Exercises> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;

    }

    @NonNull
    @Override
    public ExercisesAdapter.ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new ExercisesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesAdapter.ExercisesViewHolder holder, int position) {

        Exercises exercise = exerciseList.get(position);
        ArrayList<String> currentList = new ArrayList<String>();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();


        holder.name.setText(exercise.getName());
        holder.description.setText(exercise.getDescription());
        holder.calories.setText(String.valueOf(exercise.getCalories()) + " calories/minute");

        if(exercise.getnPeople() == 1)
        holder.nPeople.setText(String.valueOf(exercise.getnPeople()) + " person");
        else
        holder.nPeople.setText(String.valueOf(exercise.getnPeople()) + " people");


        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ExerciseSchedule.class).putExtra("exercise", exercise));
            }
        });



    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExercisesViewHolder extends RecyclerView.ViewHolder{

        TextView name,description,calories,nPeople;
        Button addButton;


        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            nPeople = itemView.findViewById(R.id.nPeople);
            calories = itemView.findViewById(R.id.calories);
            addButton = itemView.findViewById(R.id.addExercise);


        }
    }



}
