package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.projetodum.classes.User;
import com.example.projetodum.classes.WeightHistory;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.type.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MyEvolution extends AppCompatActivity {

    private Button inflate;
    private ConstraintLayout container;
    private LineChart chart;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private TextView currentWeight;
    private ArrayList<Entry> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evolution);

        data = new ArrayList<>();
        inflate = findViewById(R.id.inflate);
        chart = (LineChart) findViewById(R.id.chart);
        currentWeight = findViewById(R.id.currentWeight);
        container = findViewById(R.id.container);

        chart.setDragEnabled(true);



        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                currentWeight.setText(String.valueOf(user.getWeight()) + " kg");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());

            }
        });

        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup(view);
            }
        });



        mDatabase.getReference("WeightHistory").child(mAuth.getCurrentUser().getUid())
                .orderByKey().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int pos = 1;
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()) {


                                data.add(new Entry(pos, Float.parseFloat(dataSnapshot.getValue().toString())));
                                pos++;

                            }

                            LineDataSet dataSet = new LineDataSet(data, "Your weight");
                            LineData lineData = new LineData(dataSet);
                            chart.setData(lineData);
                            chart.getXAxis().setDrawLabels(false);
                            chart.invalidate();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Cancelled", error.getMessage());
                    }
                });
    }

    public void popup(View view) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        final PopupWindow popupWindow = new PopupWindow(popupView, 400, 300, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        Button button = popupView.findViewById(R.id.updateWeight);
        EditText newWeight = popupView.findViewById(R.id.newWeight);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String weight = String.valueOf(newWeight.getText()).trim();

                if(weight.isEmpty()){
                    newWeight.requestFocus();
                    newWeight.setError("Insert weight");
                    return;
                }

                mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User user = snapshot.getValue(User.class);
                                user.setWeight(Double.parseDouble(weight));

                                mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid())
                                        .setValue(user);

                                mDatabase.getReference("WeightHistory").child(mAuth.getCurrentUser().getUid())
                                        .child(String.valueOf(Calendar.getInstance().getTime().getTime())).setValue(weight).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                finish();
                                                startActivity(getIntent());
                                            }
                                        });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.d("Cancelled", error.getMessage());
                            }
                        });

            }
        });

    }


}