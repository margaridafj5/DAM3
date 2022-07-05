package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetodum.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlayerProfile extends AppCompatActivity {

    private TextView name, age, weight, height, imc, bw, email;
    private User profileUser, user;
    private Button follow;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String userID;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        name= findViewById(R.id.name);
        age= findViewById(R.id.playerAge);
        weight= findViewById(R.id.playerWeight);
        height= findViewById(R.id.playerHeight);
        imc= findViewById(R.id.playerIMC);
        follow = findViewById(R.id.followButton);
        bw= findViewById(R.id.playerBW);
        email= findViewById(R.id.playerEmail);
        profileUser = (User) getIntent().getSerializableExtra("user");
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        //Calculate age from bDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate bDate = LocalDate.parse(profileUser.getbDate(), formatter);
        LocalDate currentDate = Instant.ofEpochMilli(Calendar.getInstance().getTime().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        name.setText(profileUser.getfName() + " " + profileUser.getlName());
        age.setText(String.valueOf(Period.between(bDate, currentDate).getYears()));
        weight.setText(String.valueOf(profileUser.getWeight()));
        height.setText(String.valueOf(profileUser.getHeight()));
        imc.setText(String.valueOf(profileUser.getIMC()));
        bw.setText(String.valueOf(profileUser.getBW()));
        email.setText(profileUser.getEmail());


        //Get the profile user ID
        mDatabase.getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User randomUser = dataSnapshot.getValue(User.class);
                    if (profileUser.getEmail().equalsIgnoreCase(randomUser.getEmail())){
                        userID = dataSnapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());
            }
        });

        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    user = snapshot.getValue(User.class);
                    if(user.getFollowingList().contains(userID)) {
                        Log.d("Following List", "Already Following");
                        follow.setEnabled(false);
                        follow.setText("Following");
                        follow.setBackgroundColor(2);
                    }

                    System.out.println(user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());

            }
        });

        if(profileUser.getEmail().equalsIgnoreCase(mAuth.getCurrentUser().getEmail())) {
            Log.d("Following List", "Can't follow current user");
            follow.setVisibility(View.INVISIBLE);
        }

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followUser();
            }
        });




    }

    private void followUser() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        List<String> newFollowing = new ArrayList<String>();
        List<String> currentList = user.getFollowingList();
        if(!currentList.contains("empty")) {
            newFollowing = currentList;
            newFollowing.add(userID);
            user.setFollowingList(newFollowing);
            mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).setValue(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            follow.setEnabled(false);
                            follow.setText("Following");
                            follow.setBackgroundColor(2);
                            Log.d("Following List", "Updated");
                        }
                    });

        } else {
            newFollowing.add(userID);
            user.setFollowingList(newFollowing);
            mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).setValue(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            follow.setEnabled(false);
                            follow.setText("Following");
                            follow.setBackgroundColor(2);
                            Log.d("Following List", "Updated");
                        }
                    });

        }
    }
}