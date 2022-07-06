package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.projetodum.classes.Exercises;
import com.example.projetodum.classes.PlayerAdapter;
import com.example.projetodum.classes.RecyclerViewInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourFavorites extends AppCompatActivity implements RecyclerViewInterface {

    private ArrayList<Exercises> list;
    private RecyclerView recyclerView;
    private PlayerAdapter myAdapter;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_favorites);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        list = new ArrayList<>();

        recyclerView = findViewById(R.id.favoriteList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new PlayerAdapter(this, list, this);
        recyclerView.setAdapter(myAdapter);

        mDatabase.getReference("UserLike").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        printFavorite(dataSnapshot.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());

            }
        });

    }

    private void printFavorite(String Eid) {

        mDatabase.getReference("Exercises").child(Eid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Exercises exercise = snapshot.getValue(Exercises.class);
                list.add(exercise);
                myAdapter.notifyDataSetChanged();
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