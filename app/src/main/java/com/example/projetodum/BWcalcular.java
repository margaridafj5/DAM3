package com.example.projetodum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projetodum.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Locale;

public class BWcalcular extends AppCompatActivity {

    private EditText height, weight;
    private TextView result;
    private Button calculate;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bwcalcular);


        //storing the elements in variables
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        calculate = findViewById(R.id.calc);
        result = findViewById(R.id.bwResult);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();


        //finding the user's height and weight to automatically fill the elements
        mDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                height.setText(String.valueOf(user.getHeight()));
                weight.setText(String.valueOf(user.getWeight()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Calculate", error.getMessage());

            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //storing the user's input in variables
                String heightStr = String.valueOf(height.getText());
                String weightStr= String.valueOf(weight.getText());


                //bw formula and display
                if (heightStr!= null && !"".equals(heightStr) && weightStr != null && !"".equals(weightStr)){
                    float heightValue = Float.parseFloat(heightStr);
                    float weightValue = Float.parseFloat(weightStr);
                    float bmi =  weightValue / (heightValue*heightValue);

                    int a= (int) heightValue;
                    int bw = (int) (2.2 * bmi + bmi/5 + heightValue);
                    result.setText(String.valueOf(bw));
                }
            }
        });

    }
}