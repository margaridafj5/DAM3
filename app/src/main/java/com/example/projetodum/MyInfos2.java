        package com.example.projetodum;

        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import com.example.projetodum.classes.User;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import java.time.Instant;
        import java.time.LocalDate;
        import java.time.Period;
        import java.time.ZoneId;
        import java.time.format.DateTimeFormatter;
        import java.util.Calendar;


        public class MyInfos2 extends AppCompatActivity {

    TextView userUsername, userEmail, userIdade, userPeso, userAltura;
    Button addInfo;
    DatabaseReference rootRef;
    User currentUser;
    String userID;


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_infos2);


            addInfo= findViewById(R.id.addInfo);
            addInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyInfos2.this, CompleteInfo.class);
                    startActivity(intent);
                }
            });


            //Calculate age from bDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate bDate = LocalDate.parse(currentUser.getbDate(), formatter);
            LocalDate currentDate = Instant.ofEpochMilli(Calendar.getInstance().getTime().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();


            userUsername = findViewById(R.id.username);
            userIdade = findViewById(R.id.Idade);
            userAltura = findViewById(R.id.Altura);
            userEmail = findViewById(R.id.Email);
            userPeso = findViewById(R.id.Peso);


            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            rootRef = FirebaseDatabase.getInstance().getReference("Users");

            userUsername.setText(currentUser.getfName() + " " + currentUser.getlName());
            userIdade.setText(String.valueOf(Period.between(bDate, currentDate).getYears()));
            userEmail.setText(String.valueOf(currentUser.getEmail()));
            userAltura.setText(String.valueOf(currentUser.getHeight()));
            userPeso.setText(String.valueOf(currentUser.getHeight()));

        };

    }
