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
Similar al activity de Movimientos, presenta el listView de las tarjetas de débito asociadas a la cuenta.
 */
public class TarjetasAsociadas extends AppCompatActivity {

    TextView tv1;
    ListView list;
    String nom1,ap1,ced,idCuenta,saldo,monCuenta,tipoCuenta;
    String[] tarjetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_asociadas);
        tv1 = (TextView) findViewById(R.id.textView16);
        list = (ListView)findViewById(R.id.listview2);

        nom1 = getIntent().getStringExtra("Nombre1");
        ap1 = getIntent().getStringExtra("Apellido1");
        ced = getIntent().getStringExtra("Cedula");
        idCuenta = getIntent().getStringExtra("IDCuenta");
        tarjetas = getIntent().getStringArrayExtra("Tarjetas");
        saldo = getIntent().getStringExtra("Saldo");
        monCuenta = getIntent().getStringExtra("Coin");
        tipoCuenta = getIntent().getStringExtra("TipoCuenta");

        tv1.setText("Tarjetas de débito asociandos a la cuenta: " + idCuenta +"\n"
                + "Usuario: " + nom1 + " " + ap1 + "  " + "cédula: " + ced);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tarjetas);

        if(tarjetas!=null && tarjetas.length>0){
            list.setAdapter(adaptador);
        }else{
            Toast msj = Toast.makeText(getApplicationContext(),"Vacío!", Toast.LENGTH_SHORT);
            msj.show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent next = new Intent(TarjetasAsociadas.this, Cuenta.class);
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
