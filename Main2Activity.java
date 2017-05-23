package com.example.cristhian.bancatec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    Button cuenta;
    Button tarjetas;
    Button prestamos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cuenta = (Button) findViewById(R.id.button2);
        tarjetas = (Button) findViewById(R.id.button3);
        prestamos = (Button) findViewById(R.id.button4);

        cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(Main2Activity.this,Cuenta.class);
                startActivity(next);
            }
        });
        tarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(Main2Activity.this,Tarjetas.class);
                startActivity(next);
            }
        });
        prestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(Main2Activity.this,Prestamos.class);
                startActivity(next);
            }
        });
    }
}
