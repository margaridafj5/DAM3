package com.example.projetodum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projetodum.classes.User;

import java.io.Serializable;

public class PlayerProfile extends AppCompatActivity {

    private TextView fname;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_profile);

        fname= findViewById(R.id.fname);
        user = (User) getIntent().getSerializableExtra("user");

        fname.setText(user.getfName());

    }
}