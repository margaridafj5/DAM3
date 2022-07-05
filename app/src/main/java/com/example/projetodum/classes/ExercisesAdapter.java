package com.example.projetodum.classes;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetodum.R;
import com.example.projetodum.Search;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExercisesViewHolder> {

    Context context;
    ArrayList<String> ExerciseID, NameList, DescriptionList, nPeopleList, CaloriesList;

    public ExercisesAdapter(Context context, ArrayList<String> ExerciseID, ArrayList<String> NameList, ArrayList<String> DescriptionList, ArrayList<String> nPeopleList, ArrayList<String> CaloriesList ) {
        this.context = context;
        this.ExerciseID = ExerciseID;
        this.NameList = NameList;
        this.DescriptionList = DescriptionList;
        this.CaloriesList = CaloriesList;
        this.nPeopleList = nPeopleList;

    }

    @NonNull
    @Override
    public ExercisesAdapter.ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ExercisesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesAdapter.ExercisesViewHolder holder, int position) {

        String Name = NameList.get(position);
        String Description = DescriptionList.get(position);
        String Calories = CaloriesList.get(position);
        String nPeople = nPeopleList.get(position);
        String id = ExerciseID.get(position);


        holder.name.setText(Name);
        holder.description.setText(Description);
        holder.calories.setText(Calories);
        holder.nPeople.setText(nPeople);



    }

    @Override
    public int getItemCount() {
        return ExerciseID.size();
    }



}