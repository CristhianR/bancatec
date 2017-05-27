package com.example.cristhian.bancatec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/*
Este activity se encarga de presentar el lisView de todas las transacciones asociadas a la cuenta(consultada)
 del cliente, la lista de datos es obtenida como parámetro por el activity Cuenta.
 */
public class Movimientos extends AppCompatActivity {

    TextView tv1;
    String nom1,ap1,ced,idCuenta,saldo,monCuenta,tipoCuenta;
    ListView list;
    String[] movimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        tv1 = (TextView) findViewById(R.id.textView13);
        list = (ListView)findViewById(R.id.listview);

        nom1 = getIntent().getStringExtra("Nombre1");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        movimientos = getIntent().getStringArrayExtra("Movimientos");
        saldo = getIntent().getStringExtra("Saldo");
        monCuenta = getIntent().getStringExtra("Coin");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movimientos);

        tv1.setText("Movimientos asociandos a la cuenta: " + idCuenta +"\n"
        + "Usuario: " + nom1 + " " + ap1 + "  " + "cédula: " + ced);

        if(movimientos!=null && movimientos.length>0){
            list.setAdapter(adaptador);
        }else{
            Toast msj = Toast.makeText(getApplicationContext(),"Vacío!", Toast.LENGTH_SHORT);
            msj.show();
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent next = new Intent(Movimientos.this, Cuenta.class);
            next.putExtra("Nombre1", nom1);
            next.putExtra("Apellido1", ap1);
            next.putExtra("Cedula", ced);
            next.putExtra("Saldo", saldo);
            next.putExtra("Coin", monCuenta);
            next.putExtra("TipoCuenta", tipoCuenta);
            next.putExtra("IDCuenta", idCuenta);
            startActivity(next);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
