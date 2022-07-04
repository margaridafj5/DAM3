package com.example.projetodum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class FirstPage extends AppCompatActivity {

    Button logout, createExercise, profile;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        System.out.println(Login.isAdmin);

        mAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.logout);
        createExercise = findViewById(R.id.createExercise);
        profile = findViewById(R.id.profile);
        if(Login.isAdmin != 1) createExercise.setVisibility(View.INVISIBLE);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getInstance().signOut();
                Intent intent = new Intent(FirstPage.this, Login.class);
                startActivity(intent);
            }
        });

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

    }
}