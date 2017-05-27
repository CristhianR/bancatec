package com.example.cristhian.bancatec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
Este activity presenta el listView de las compras realizadas con una tarjeta de crédito en específico.
 */
public class Listado extends AppCompatActivity {

    TextView tv1;
    String nom1,ap1,ced,idtar,idCuenta,monCuenta,tipoCuenta,saldo;
    ListView list;
    String[] compras,tarCredito,ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        tv1 = (TextView) findViewById(R.id.textView20);
        list = (ListView)findViewById(R.id.listview4);
        nom1 = getIntent().getStringExtra("Nombre1");
        saldo = getIntent().getStringExtra("Saldo");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        idtar = getIntent().getStringExtra("IDTarjeta");
        compras = getIntent().getStringArrayExtra("Compras");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tarCredito = getIntent().getStringArrayExtra("Tarjetas");
        ids = getIntent().getStringArrayExtra("IDTar");
        monCuenta = getIntent().getStringExtra("Coin");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, compras);

        tv1.setText("Listado de compras realizadas con la tarjeta: " + idtar +"\n"
                + "Usuario: " + nom1 + " " + ap1 + "  " + "cédula: " + ced);

        if(compras!=null && compras.length>0){
            list.setAdapter(adaptador);
        }else{
            Toast msj = Toast.makeText(getApplicationContext(),"Vacío!", Toast.LENGTH_SHORT);
            msj.show();
        }



    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent next = new Intent(Listado.this, Tarjeta.class);
            next.putExtra("Nombre1", nom1);
            next.putExtra("Apellido1", ap1);
            next.putExtra("Cedula", ced);
            next.putExtra("Saldo", saldo);
            next.putExtra("Coin", monCuenta);
            next.putExtra("TipoCuenta", tipoCuenta);
            next.putExtra("IDtarjeta", idtar);
            next.putExtra("Tarjetas", tarCredito);
            next.putExtra("IDTar", ids);
            next.putExtra("IDCuenta", idCuenta);
            startActivity(next);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
