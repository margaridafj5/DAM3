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
import com.example.projetodum.classes.Schedule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private Button scheduleButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private String Uid;
    private ArrayList<String> exerciseList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_schedule);

        //storing elements in variables
        exerciseName = findViewById(R.id.eName);
        exerciseDescription = findViewById(R.id.eDescription);
        exerciseNumber = findViewById(R.id.eNumber);
        exerciseCalories = findViewById(R.id.eCalories);
        scheduleButton = findViewById(R.id.schedule);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        //getting the object of the exercise we were redirected from
        exercise = (Exercises) getIntent().getSerializableExtra("exercise");

        //displaying the exercise's info
        exerciseName.setText(exercise.getName());
        exerciseDescription.setText(exercise.getDescription());
        exerciseNumber.setText(String.valueOf(exercise.getnPeople()));
        exerciseCalories.setText(String.valueOf(exercise.getCalories()));

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog();
            }
        });

        Uid = mAuth.getCurrentUser().getUid();

        //verifying if the user has already scheduled this exercise, and if he did, disabling the schedule button
        mDatabase.getReference("UserExercise").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    //verify
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Schedule schedule = dataSnapshot.getValue(Schedule.class);
                        exerciseList.add(schedule.getEid());
                    }

                    //disable
                    if(exerciseList.contains(exercise.getEid())) {
                        scheduleButton.setEnabled(false);
                        scheduleButton.setText("Already Scheduled");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());
            }
        });


    }

    //method for displaying the date time dialog and store the schedule in the database
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
                            Schedule schedule = new Schedule(exercise.getEid(), 0, simpleDateFormat.format(calendar.getTime()));

                            //storing the schedule in the database if it doesn't exist already
                            mDatabase.getReference("UserExercise").child(Uid).child("0").setValue(schedule).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    scheduleButton.setEnabled(false);
                                    scheduleButton.setText("Already Scheduled");
                                }
                            });
                        } else {

                            //if schedule already exists, disable button
                            Schedule schedule = new Schedule(exercise.getEid(), 0, simpleDateFormat.format(calendar.getTime()));
                            mDatabase.getReference("UserExercise").child(Uid).child(String.valueOf(exerciseList.size())).setValue(schedule).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    scheduleButton.setEnabled(false);
                                    scheduleButton.setText("Already Scheduled");
                                }
                            });
                        }

                    }
                };

                new TimePickerDialog(ExerciseSchedule.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();


            }
        };

        new DatePickerDialog(ExerciseSchedule.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
}