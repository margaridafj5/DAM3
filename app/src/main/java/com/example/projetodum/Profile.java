package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.projetodum.classes.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    String userID;

    TextView name, following, following_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.smallsquare);
        name = findViewById(R.id.name);
        following = findViewById(R.id.following);
        following_count = findViewById(R.id.following_count);

        userID = mAuth.getCurrentUser().getUid();

        mDatabase.getReference("Users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.getValue(User.class).getfName() + " " + snapshot.getValue(User.class).getlName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", error.getMessage());

            }
        });

        tabLayout.setupWithViewPager(viewPager);

        ProfileTabsMY profileTabsMY = new ProfileTabsMY(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        profileTabsMY.addFragment(new MyExercises(),"ex");
        profileTabsMY.addFragment(new MyInfos(),"infos");
        profileTabsMY.addFragment(new CalculateThings(),"calc");
        viewPager.setAdapter(profileTabsMY);

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Follow.class));
            }
        });

        following_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, Follow.class));
            }
        });


    }
}