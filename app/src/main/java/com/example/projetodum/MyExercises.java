package com.example.projetodum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projetodum.classes.Exercises;
import com.example.projetodum.classes.PlayerAdapter;
import com.example.projetodum.classes.RecyclerViewInterface;
import com.example.projetodum.classes.Schedule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyExercises extends AppCompatActivity implements RecyclerViewInterface {

    private ArrayList<Exercises> list;
    private RecyclerView recyclerView;
    private PlayerAdapter myAdapter;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.myExerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new PlayerAdapter(this, list, this);
        recyclerView.setAdapter(myAdapter);

        mDatabase.getReference("UserExercise").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Schedule schedule = dataSnapshot.getValue(Schedule.class);
                        printScheduleList(schedule.getEid());
                    }
                } else {
                    System.out.println("Hellooooooooooo");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());

            }
        });
    }

    private void printScheduleList(String Eid) {

        mDatabase.getReference("Exercises").child(Eid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Exercises exercise = snapshot.getValue(Exercises.class);
                    list.add(exercise);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());

            }
        });



    }


    @Override
    public void onItemClick(int position, Button favorite) {
        //Obligatory method
    }
}