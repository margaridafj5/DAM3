package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.projetodum.classes.Exercises;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("deprecation")
public class ExerciseSchedule extends AppCompatActivity {

    private Exercises exercise;
    private TextView exerciseName, exerciseDescription, exerciseNumber, exerciseCalories;
    private Button schedule;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private String Uid;
    private ArrayList<String> exerciseList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_schedule);

        exerciseName = findViewById(R.id.eName);
        exerciseDescription = findViewById(R.id.eDescription);
        exerciseNumber = findViewById(R.id.eNumber);
        exerciseCalories = findViewById(R.id.eCalories);
        schedule = findViewById(R.id.schedule);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        exercise = (Exercises) getIntent().getSerializableExtra("exercise");

        exerciseName.setText(exercise.getName());
        exerciseDescription.setText(exercise.getDescription());
        exerciseNumber.setText(String.valueOf(exercise.getnPeople()));
        exerciseCalories.setText(String.valueOf(exercise.getCalories()));

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog();
            }
        });

        Uid = mAuth.getCurrentUser().getUid();

        mDatabase.getReference("UserExercise").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        exerciseList.add(dataSnapshot.getValue().toString());
                    }

                    if(exerciseList.contains(exercise.getEid())) {
                        schedule.setEnabled(false);
                        schedule.setText("Already Scheduled");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());
            }
        });


    }

    private void showDateTimeDialog() {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy HH:mm");
                        if(exerciseList.isEmpty()) {
                            mDatabase.getReference("UserExercise").child(Uid).child("0").setValue()
                        }

                    }
                };

                new TimePickerDialog(ExerciseSchedule.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();


            }
        };

        new DatePickerDialog(ExerciseSchedule.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
}