package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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
    private LinearLayout yourFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_exercises);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        list = new ArrayList<>();

        recyclerView = findViewById(R.id.myExerciseList);
        yourFavorites = findViewById(R.id.buttonFavorite);
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

        yourFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyExercises.this, YourFavorites.class));
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