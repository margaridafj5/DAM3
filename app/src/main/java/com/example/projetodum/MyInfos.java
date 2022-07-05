package com.example.projetodum;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetodum.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MyInfos extends Fragment {

    TextView userUsername, userEmail, userIdade, userPeso, userIMC, userBW, userAltura;
    DatabaseReference rootRef;
    User currentUser;
    String userID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_infos, container, false);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userUsername = view.findViewById(R.id.username);
        userIdade = view.findViewById(R.id.Idade);
        userAltura = view.findViewById(R.id.Altura);
        userEmail = view.findViewById(R.id.Email);
        userPeso = view.findViewById(R.id.Peso);
        userIMC = view.findViewById(R.id.IMC);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootRef = FirebaseDatabase.getInstance().getReference("Users");

        //Buscar os dados do utilizador na base de dados
        rootRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Calcular a idade pela data de nascimento
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate bDate = LocalDate.parse(snapshot.getValue(User.class).getbDate(), formatter);
                LocalDate currentDate = Instant.ofEpochMilli(Calendar.getInstance().getTime().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                //User Age
                String age = String.valueOf(Period.between(bDate, currentDate).getYears());


                //Dar display dos dados
                userUsername.setText(snapshot.getValue(User.class).getfName() + " " + snapshot.getValue(User.class).getlName());
                userIdade.setText(age);
                userEmail.setText(snapshot.getValue(User.class).getEmail());
                userIMC.setText(String.valueOf(snapshot.getValue(User.class).getIMC()));
                userBW.setText(String.valueOf(snapshot.getValue(User.class).getBW()));
                userPeso.setText(String.valueOf(snapshot.getValue(User.class).getWeight()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Cancelled", error.getMessage());
            }
        });



        // Inflate the layout for this fragment
    }
}