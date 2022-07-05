package com.example.projetodum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BWcalcular extends AppCompatActivity {

    private EditText height;
    private EditText weight;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bwcalcular);

        height= (EditText) findViewById(R.id.height);
        weight= (EditText) findViewById(R.id.weight);
        result= (TextView) findViewById(R.id.result);

    }

    public void calculateIMC (View view1) {
        String heightStr = height.getText().toString();
        String weightStr= weight.getText().toString();

        if (heightStr!= null && !"".equals(heightStr) && weightStr != null && !"".equals(weightStr)){
            float heightValue = Float.parseFloat(heightStr) / 100;
            float weightValue = Float.parseFloat(weightStr);
            float bmi =  weightValue / (heightValue*heightValue);
            displayBW(bmi, heightValue);
        }
    }

    private void displayBW(float bmi, float heightValue){
        int a= (int) heightValue;
        int bw = (int) (2.2 * bmi + bmi/5 + heightValue);
        result.setText(bw);
    }
}