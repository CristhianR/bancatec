package com.example.cristhian.bancatec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Transacciones extends AppCompatActivity {

    String ced,monCuenta,idCuenta,tipoCuenta,saldo;
    Button trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacciones);
        trans = (Button) findViewById(R.id.button13);
        ced = getIntent().getStringExtra("Cedula");
        saldo = getIntent().getStringExtra("Saldo");
        monCuenta = getIntent().getStringExtra("Moneda");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");

        final Toast msj = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT);

        trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msj.setText("¡POR IMPLEMENTAR!");
                msj.show();

            }
        });
    }
}
