package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetodum.classes.User;
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

import java.util.Date;

public class Register extends AppCompatActivity {


    EditText fname, sname, password, email, bdate;
    RadioGroup gender;
    RadioButton male, female, other;
    TextView login;
    Button submit;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String registerGender;
    int adminId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        fname = findViewById(R.id.firstName);
        sname = findViewById(R.id.sureName);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        bdate = findViewById(R.id.birthDate);
        gender = findViewById(R.id.gender);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        other = findViewById(R.id.other);
        login = findViewById(R.id.login);
        submit = findViewById(R.id.submit);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    public void registerUser(){

        String registerName = fname.getText().toString().trim();
        String registerSurename = sname.getText().toString().trim();
        String registerEmail = email.getText().toString().trim();
        String registerPassword = password.getText().toString().trim();
        String registerDate = bdate.getText().toString().trim();
        int registerGroup = gender.getCheckedRadioButtonId();
        registerGender = "empty";


        if (registerGroup == male.getId()){
            registerGender = "male";
        } else if (registerGroup == female.getId()){
            registerGender = "female";
        } else if (registerGroup == other.getId()){
            registerGender = "other";
        }

        if(registerName.isEmpty()) {
            fname.setError("Campo obrigatório!");
            fname.requestFocus();
            return;
        }

        if(registerSurename.isEmpty()){
            sname.setError("Campo obrigatório!");
            fname.requestFocus();
            return;
        }

        if(registerEmail.isEmpty()){
            email.setError("Campo obrigatório!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(registerEmail).matches()){
            email.setError("Tem de inserir um email válido!");
            email.requestFocus();
            return;
        }

        if(registerPassword.isEmpty()){
            password.setError("Campo obrigatório!");
            password.requestFocus();
            return;
        }

        if(registerPassword.length() < 8) {
            password.setError("Password demasiado pequena!");
            password.requestFocus();
            return;
        }

        if(registerDate.isEmpty()){
            bdate.setError("Campo obrigatório!");
            bdate.requestFocus();
            return;
        }

        if(registerGender == "empty"){
            gender.requestFocus();
            Toast.makeText(Register.this, "Escolha um género", Toast.LENGTH_LONG).show();
            return;
        }

        String finalRegisterGender = registerGender;
        mAuth.createUserWithEmailAndPassword(registerEmail, registerPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            String id = mAuth.getCurrentUser().getUid();

                            database.getReference().child("Admin").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(!task.getResult().exists()) {

                                        database.getReference("Admin").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.exists()){
                                                    adminId = (int)snapshot.getChildrenCount();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.d("Exception", error.toString());

                                            }
                                        });
                                        database.getReference("Admin").child(id).setValue(adminId);
                                        Log.d("Admin", "New admin added");
                                    } else {
                                        Log.d("Admin", task.getResult().toString());
                                    }
                                }
                            });

                            DatabaseReference ref = database.getReference("Users");
                            User user = new User(registerEmail, registerName, registerSurename, registerGender, registerDate, 0, 0);
                            ref.child(id).setValue(user);

                            FirebaseUser newUser = mAuth.getCurrentUser();
                            newUser.sendEmailVerification();

                            Toast.makeText(Register.this, "O utilizador foi registado com sucesso!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Register.this, Login.class));


                        }

                    }
                });






    }
}