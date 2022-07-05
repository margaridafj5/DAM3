package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.projetodum.classes.User;
import com.example.projetodum.classes.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements UserAdapter.OnNoteListener {

    RecyclerView recyclerView;
    ArrayList<User> userList;
    UserAdapter adapter;
    FirebaseDatabase mDatabase;
    FirebaseAuth mAuth;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mDatabase = FirebaseDatabase.getInstance();
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.userList);
        userList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, userList, this);
        recyclerView.setAdapter(adapter);



        mDatabase.getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    mDatabase.getReference("Admin").child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot2) {
                            if(snapshot2.getValue() == null) {
                                User user = dataSnapshot.getValue(User.class);
                                userList.add(user);
                            }

                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("Cancelled", error.getMessage());
                        }
                    });


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
    }

    private void filterList(String newText) {

            ArrayList<User> newList = new ArrayList<>();
            for(User user : userList) {
                if (user.getEmail().contains(newText)){
                    newList.add(user);
                }
            }

            adapter.setFilteredList(newList);
    }

    @Override
    public void onNoteClick(int position) {
        startActivity(new Intent(Search.this, PlayerProfile.class).putExtra("user", userList.get(position)));
    }
}