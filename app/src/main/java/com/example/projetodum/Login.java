package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button login;
    EditText email, password;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    TextView register;
    public static int isAdmin = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        register = findViewById(R.id.register);
        login = findViewById(R.id.loginSubmit);
        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logUser();
            }
        });



    }

    public void logUser() {

        String loginEmail = email.getText().toString().trim();
        String loginPassword = password.getText().toString().trim();

        if(loginEmail.isEmpty()) {
            email.setError("Insira o Email");
            email.requestFocus();
            return;
        }

        if(loginPassword.isEmpty()) {
            password.setError("Insira o Email");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            database.getReference("Admin").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.getValue() != null) {
                                        isAdmin = 1;
                                    } else {
                                        isAdmin = 0 ;
                                    }
                                    if(snapshot.getValue() != null || user.isEmailVerified()) {
                                        Log.d("Success", "User is verified");
                                        startActivity(new Intent(Login.this, FirstPage.class));
                                    } else {
                                        user.sendEmailVerification();
                                        Log.d("Failure", "User is not verified");
                                        Toast.makeText(Login.this, "Verify your email!", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.d("Cancelled", error.toString());
                                }
                            });

                        }else{
                            Log.d("Cancelled", "Login failed");
                            Toast.makeText(Login.this, "Login falhado", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}