package com.example.projetodum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CalculateThings extends AppCompatActivity {

    private Button IMC, BW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_things);

        /*
        * this page is just two buttons redirecting to their respective pages
        *  */


        IMC= findViewById(R.id.IMC);

        IMC.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View bw) {
                startActivity(new Intent(CalculateThings.this, IMCcalcular.class));
            }
        });

        BW= findViewById(R.id.BW);
        BW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View bw) {
                startActivity(new Intent(CalculateThings.this, BWcalcular.class));
            }
        });
    }

}