package com.example.projetodum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MyInfos extends Fragment {

    EditText username,userEmail, userIdade, userLoc, userPeso, userIMC, userBW;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_infos, container, false);
    }

    username = findViewById(R.id.username);
    userIdade = findViewById(R.id.Idade);
    userEmail = findViewById(R.id.Email);
    userLoc = findViewById(R.id.Loc);
    userPeso= findViewById(R.id.Peso);
    userIMC = findViewById(R.id.IMC);
    userBW = findViewById(R.id.BW);

    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    rootRef = FirebaseDatabase.getInstance("https://projetoddam-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users");
    uidRef = rootRef.child(uid);

    //buscar dados do utilizador e disposiciona-los na pagina
        uidRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            String email = snapshot.child("email").getValue().toString();
            String nome = snapshot.child("nome").getValue().toString();
            String genero = snapshot.child("generouser").getValue().toString();
            String idade = snapshot.child("idade").getValue().toString();
            String morada = snapshot.child("morada").getValue().toString();
            String votesLeft = snapshot.child("numVotosDisponiveis").getValue().toString();

            userEmail.setText(" " + email);
            username.setText(" " + username);
            userIdade.setText(" " + idade);
            userPeso.setText(" " + genero);
            userIMC.setText(" " + morada);
            userLoc.setText(" " + username);
            userBW.setText(" " + username);


        }



}