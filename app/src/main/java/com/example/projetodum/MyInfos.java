package com.example.projetodum;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyInfos extends Fragment {

    EditText userUsername,userEmail, userIdade, userLoc, userPeso, userIMC, userBW;
    DatabaseReference idRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_infos, container, false);

    userUsername = findViewById(R.id.username);
    userIdade = findViewById(R.id.Idade);
    userEmail = findViewById(R.id.Email);
    userLoc = findViewById(R.id.Loc);
    userPeso= findViewById(R.id.Peso);
    userIMC = findViewById(R.id.IMC);
    userBW = findViewById(R.id.BW);

    id = FirebaseAuth.getInstance().getCurrentUser().getid();
    rootRef = FirebaseDatabase.getInstance("https://projetodum-9ff4d-default-rtdb.firebaseio.com/").getReference("Users");
    idRef = rootRef.child(id);

    //buscar dados do utilizador e disposiciona-los na pagina
        idRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot){
            String email = snapshot.child("email").getValue().toString();
            String username = snapshot.child("username").getValue().toString();
            String IMC = snapshot.child("IMC").getValue().toString();
            String idade = snapshot.child("idade").getValue().toString();
            String loc = snapshot.child("loc").getValue().toString();

            userEmail.setText(" " + userEmail);
            userUsername.setText(" " + userUsername);
           // userIdade.setText(" " + userIdade);
            userPeso.setText(" " + userPeso);
            userIMC.setText(" " + userIMC);
            userLoc.setText(" " + userLoc);
            userBW.setText(" " + userBW);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }


}