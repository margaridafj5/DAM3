package com.example.projetodum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CompleteInfo extends AppCompatActivity {

    EditText peso, altura, loc;
    Button skip;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);


        peso = findViewById(R.id.Peso);
        altura = findViewById(R.id.Altura);
        loc = findViewById(R.id.Loc);
        submit = findViewById(R.id.submit);
        skip = findViewById(R.id.skip);


    }
}