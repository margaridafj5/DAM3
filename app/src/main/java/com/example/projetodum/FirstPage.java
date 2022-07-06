package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.projetodum.classes.Adapter;
import com.example.projetodum.classes.Exercises;
import com.example.projetodum.classes.ExercisesAdapter;
import com.example.projetodum.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirstPage extends AppCompatActivity {

    private LinearLayout createExercise, profile, search;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private ExercisesAdapter myAdapter;
    private FirebaseDatabase mDatabase;
    private ArrayList<Exercises> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        System.out.println(Login.isAdmin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        recyclerView = findViewById(R.id.exerciseList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        search = findViewById(R.id.search);
        createExercise = findViewById(R.id.createExercise);
        profile = findViewById(R.id.profile);
        myAdapter = new ExercisesAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User tmp = snapshot.getValue(User.class);
                if(tmp.getHeight() == 0 || tmp.getWeight() == 0){
                    startActivity(new Intent(FirstPage.this, CompleteInfo.class). putExtra("flag", 1));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());

            }
        });

        mDatabase.getReference("Exercises").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Exercises exercise = dataSnapshot.getValue(Exercises.class);
                    list.add(exercise);
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());

            }
        });

        if(Login.isAdmin != 1) createExercise.setVisibility(View.GONE);



        createExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstPage.this, BackOffice.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstPage.this, Profile.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstPage.this, Search.class));
            }
        });

    }
}