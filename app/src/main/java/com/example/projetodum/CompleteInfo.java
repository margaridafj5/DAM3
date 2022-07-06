package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projetodum.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompleteInfo extends AppCompatActivity {

    EditText peso, altura;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);


        peso = findViewById(R.id.Peso);
        altura = findViewById(R.id.Altura);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeInfo();
            }
        });


    }

    private void completeInfo() {

        String mPeso = String.valueOf(peso.getText());
        String mAltura = String.valueOf(altura.getText());

        if(mPeso.isEmpty()) {
            peso.setError("Obligatory field");
            peso.requestFocus();
            return;
        }

        if(mAltura.isEmpty()) {
            altura.requestFocus();
            altura.setError("Obligatory field");
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                user.setWeight(Double.parseDouble(mPeso));
                user.setHeight(Double.parseDouble(mAltura));

                mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(CompleteInfo.this, FirstPage.class));
                        finish();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());
            }
        });


    }

}