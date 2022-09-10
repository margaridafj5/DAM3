package com.example.projetodum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetodum.classes.Adapter;
import com.example.projetodum.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Follow extends AppCompatActivity implements Adapter.OnNoteListener {

    private RecyclerView recyclerView;
    private Adapter myAdapter;
    private ArrayList<User> list;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //initializing recycler view
        recyclerView = findViewById(R.id.followList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter = new Adapter(this, list, this);
        recyclerView.setAdapter(myAdapter);


        //finding the list of users this user is currently following and passing it to the method that's going to display it in the recycler view
        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(!user.getFollowingList().contains("empty"))
                addUser(user.getFollowingList());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());
            }
        });

    }

    //redirecting to following user profile when clicked
    @Override
    public void onNoteClick(int position) {
        startActivity(new Intent(Follow.this, PlayerProfile.class).putExtra("user", list.get(position)));
    }

    //method used to display the following list in the recycler view
    private void addUser(List<String> tempList) {

        list.clear();
        for(int i = 0; i<tempList.size(); i++) {
            mDatabase.getReference("Users").child(tempList.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    list.add(user);
                    myAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Cancelled", error.getMessage());

                }
            });
        }

    }
}
