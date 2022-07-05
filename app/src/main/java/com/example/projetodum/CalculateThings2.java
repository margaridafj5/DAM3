package com.example.projetodum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CalculateThings2 extends AppCompatActivity {

    Button IMC, bw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_things2);

    IMC= findViewById(R.id.IMC);

      IMC.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View bw) {
            startActivity(new Intent(CalculateThings2.this, IMCcalcular.class));
        }
    });
    }

}