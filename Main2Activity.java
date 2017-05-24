package com.example.cristhian.bancatec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    Button cuenta;
    Button tarjetas;
    Button prestamos;
    String nom1,nom2,ap1,ap2,ced,tipo,tel,dir,ing,monIngreso,con,saldo,tipoCuenta,monCuenta,idCuenta;
    TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        cuenta = (Button) findViewById(R.id.button2);
        tarjetas = (Button) findViewById(R.id.button3);
        prestamos = (Button) findViewById(R.id.button4);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView8);

        nom1 = getIntent().getStringExtra("Nombre1");
        nom2 = getIntent().getStringExtra("Nombre2");
        ap1 = getIntent().getStringExtra("Apellido1");
        ap2 = getIntent().getStringExtra("Apellido2");
        ced = getIntent().getStringExtra("Cedula");
        tipo = getIntent().getStringExtra("Tipo");
        tel = getIntent().getStringExtra("Telefono");
        dir = getIntent().getStringExtra("Direccion");
        ing = getIntent().getStringExtra("Ingreso");
        monIngreso = getIntent().getStringExtra("Moneda");
        con = getIntent().getStringExtra("Contra");
        saldo = getIntent().getStringExtra("Saldo");
        monCuenta = getIntent().getStringExtra("Coin");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");

        tv1.setText("Bienvenido a BancaTec señor(a) " + nom1 + " " + ap1);
        tv2.setText("Su número de cuenta es: " + idCuenta);

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent next = new Intent(Main2Activity.this, Cuentas.class);
            startActivity(next);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
