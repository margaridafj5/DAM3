package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projetodum.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IMCcalcular extends AppCompatActivity {

    private EditText height, weight;
    private Button calculate;
    private TextView result;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imccalcular);

        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        calculate = findViewById(R.id.calc);
        height= findViewById(R.id.height);
        weight= findViewById(R.id.weight);
        result= findViewById(R.id.imcResult);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                height.setText(String.valueOf(user.getHeight()));
                weight.setText(String.valueOf(user.getWeight()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Calculate", error.getMessage());

            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateIMC();
            }
        });

    }

    private void calculateIMC () {
        String heightStr = height.getText().toString();
        String weightStr= weight.getText().toString();

        if (heightStr!= null && !"".equals(heightStr) && weightStr != null && !"".equals(weightStr)){
            float heightValue = Float.parseFloat(heightStr) / 100;
            float weightValue = Float.parseFloat(weightStr);

            float bmi =  weightValue / (heightValue*heightValue);
            displayBMI(bmi);
        }
    }

    private void displayBMI(float bmi){
        String bmiLabel= "";

        if(Float.compare(bmi, 15f) <= 0){
            bmiLabel= getString(R.string.very_severely_underweight);}

            else if (Float.compare(bmi, 15f) > 0 && Float.compare(bmi, 16f) <= 0){
                bmiLabel = getString(R.string.severely_underweight);        }

        else if (Float.compare(bmi, 16f) > 0 && Float.compare(bmi, 18.5f) <= 0){
            bmiLabel = getString(R.string.underweight);        }

        else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0){
            bmiLabel = getString(R.string.normal);        }

        else if (Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 30f) <= 0){
            bmiLabel = getString(R.string.overweight);
        }

        else if (Float.compare(bmi, 30f) > 0 && Float.compare(bmi, 35f) <= 0){
            bmiLabel = getString(R.string.obese_class_ii);        }

        else if (Float.compare(bmi, 35f) > 0 && Float.compare(bmi, 40f) <= 0){
            bmiLabel = getString(R.string.obese_class_iii);        }

        bmiLabel= bmi + "\n\n"+ bmiLabel;
        result.setText(bmiLabel);
    }
}