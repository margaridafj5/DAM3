package com.example.projetodum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetodum.classes.Exercises;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class BackOffice extends AppCompatActivity {

    private DatabaseReference database;
    EditText eName, eDescription, eCalories, enPeople;
    Button eSubmit;
    TextView eSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_office);


        //storing elements in variables
        eName = findViewById(R.id.exerciseName);
        enPeople = findViewById(R.id.exercisenPeople);
        eDescription = findViewById(R.id.exerciseDescription);
        eCalories = findViewById(R.id.exerciseCalories);
        eSubmit = findViewById(R.id.exerciseSubmit);
        eSuccess = findViewById(R.id.success);

        database = FirebaseDatabase.getInstance().getReference();


        //if user is not admin, redirect to first page
        if(Login.isAdmin != 1) {
            Log.d("Redirect", "Not Admin");
            startActivity(new Intent(BackOffice.this, FirstPage.class));
        }

        eSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitExercise();
            }
        });

    }

    public void submitExercise(){

        //storing content in variables
        String name = eName.getText().toString().trim();
        String description = eDescription.getText().toString().trim();
        String calories = eCalories.getText().toString().trim();
        String nPeople = enPeople.getText().toString().trim();


        //content verification
        if(name.isEmpty()) {
            eName.setError("Insert the name of the exercise");
            eName.requestFocus();
            return;
        }

        if(description.isEmpty()) {
            eDescription.setError("Insert the description of the exercise");
            eDescription.requestFocus();
            return;
        }

        if(calories.isEmpty()){
            eCalories.setError("Insert the number of calories per minute of the exercise");
            eCalories.requestFocus();
            return;
        }

        if(nPeople.isEmpty()) {
            eDescription.setError("Insert the number of persons advised for this exercise");
            eDescription.requestFocus();
            return;
        }


        //creating an unique id for the exercises and creating an object
        String eID = UUID.randomUUID().toString();
        Exercises exercise = new Exercises(name, description, Integer.parseInt(calories), Integer.parseInt(nPeople), eID);


        //storing the exercise in the database and clearing the elements' text
        database.child("Exercises").child(eID).setValue(exercise).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(BackOffice.this, "Exercise created successfully!", Toast.LENGTH_LONG).show();
                    eName.setText("");
                    eDescription.setText("");
                    eCalories.setText("");
                    enPeople.setText("");
                    eSuccess.setVisibility(View.VISIBLE);

                }
            }
        });

    }
}