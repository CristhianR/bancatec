package com.example.cristhian.bancatec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TarjetasAsociadas extends AppCompatActivity {

    TextView tv1;
    ListView list;
    String nom1,ap1,ced,idCuenta;
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
}
