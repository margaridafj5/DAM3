package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.projetodum.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class MyInfos extends AppCompatActivity {

    private TextView userUsername, userEmail, userIdade, userPeso, userAltura;
    private Button addInfo;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private User currentUser;
    private int flag;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_infos);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        addInfo= findViewById(R.id.addInfo);
        userUsername = findViewById(R.id.username);
        userIdade = findViewById(R.id.Idade);
        userAltura = findViewById(R.id.Altura);
        userEmail = findViewById(R.id.Email);
        userPeso = findViewById(R.id.Peso);

        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentUser = snapshot.getValue(User.class);

                        //Calculate age from bDate
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate bDate = LocalDate.parse(currentUser.getbDate(), formatter);
                        LocalDate currentDate = Instant.ofEpochMilli(Calendar.getInstance().getTime().getTime())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();

                        userUsername.setText(currentUser.getfName() + " " + currentUser.getlName());
                        userIdade.setText(String.valueOf(Period.between(bDate, currentDate).getYears()));
                        userEmail.setText(String.valueOf(currentUser.getEmail()));
                        userAltura.setText(String.valueOf(currentUser.getHeight()));
                        userPeso.setText(String.valueOf(currentUser.getWeight()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Cancelled", error.getMessage());

                    }
                });


        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyInfos.this, CompleteInfo.class);
                startActivity(intent);
            }
        });

    };

}
