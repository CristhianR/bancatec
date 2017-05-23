package com.example.cristhian.bancatec;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tarjetas extends AppCompatActivity {

    Button consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);
        consulta = (Button) findViewById(R.id.button10);

        consulta .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(Tarjetas.this, Tarjeta.class);
                startActivity(next);
            }
        });
    }
}
